package igor.kuridza.dice.movieapp.ui.fragments.search.tv_shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchTvShowsViewModel(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val _tvShows = MutableLiveData<Resource<GetTvShowsResponse>>()
    val tvShows: LiveData<Resource<GetTvShowsResponse>>
        get() = _tvShows

    fun searchTvShowsByQuery(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowRepository.searchTvShows(searchQuery).collect { data ->
                _tvShows.postValue(data)
            }
        }
    }
}