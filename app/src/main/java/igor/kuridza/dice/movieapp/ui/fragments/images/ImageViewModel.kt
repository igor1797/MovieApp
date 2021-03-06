package igor.kuridza.dice.movieapp.ui.fragments.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.common.MOVIE
import igor.kuridza.dice.movieapp.common.TV_SHOW
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImageViewModel(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val _images = MutableLiveData<Resource<GetImagesResponse>>()
    val images: LiveData<Resource<GetImagesResponse>>
        get() = _images

    fun getImages(id: Int, type: String) {
        viewModelScope.launch(IO) {
            when (type) {
                TV_SHOW -> {
                    tvShowRepository.getImagesThatBelongToTvShow(id).collect { data ->
                        _images.postValue(data)
                    }
                }
                MOVIE -> {
                    movieRepository.getImagesThatBelongToMovie(id).collect { data ->
                        _images.postValue(data)
                    }
                }
            }
        }
    }
}