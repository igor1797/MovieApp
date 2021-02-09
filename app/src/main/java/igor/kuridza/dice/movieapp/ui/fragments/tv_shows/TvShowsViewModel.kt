package igor.kuridza.dice.movieapp.ui.fragments.tv_shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.common.ON_THE_AIR
import igor.kuridza.dice.movieapp.common.POPULAR
import igor.kuridza.dice.movieapp.common.TOP_RATED
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import igor.kuridza.dice.movieapp.utils.resource.ResourceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TvShowsViewModel(
    private val tvShowRepository: TvShowRepository,
    private val resourceHelper: ResourceHelper
) : ViewModel() {

    private val categories = mapOf(
        TOP_RATED to resourceHelper.topRatedString(),
        POPULAR to resourceHelper.popularString(),
        ON_THE_AIR to resourceHelper.onTheAirString()
    )

    fun getCategoryNameByKey(category: String): String {
        return categories[category].toString()
    }

    private val _category = MutableLiveData(TOP_RATED)
    val category: LiveData<String>
        get() = _category

    private val _tvShows = MutableLiveData<Resource<GetTvShowsResponse>>()
    val tvShows: LiveData<Resource<GetTvShowsResponse>>
        get() = _tvShows

    fun getTvShowsByType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowRepository.getTvShowsByType(type).collect { data ->
                _tvShows.postValue(data)
            }
        }
    }

    fun changeCategory(movieCategory: String) {
        _category.value = movieCategory
    }
}