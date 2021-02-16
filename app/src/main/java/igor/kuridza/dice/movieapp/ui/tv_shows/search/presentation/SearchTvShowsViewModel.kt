package igor.kuridza.dice.movieapp.ui.tv_shows.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchTvShowsViewModel(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val _tvShows = MutableLiveData<ViewState<GetTvShowsResponse>>()
    val tvShows: LiveData<ViewState<GetTvShowsResponse>>
        get() = _tvShows

    fun searchTvShowsByQuery(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowRepository.searchTvShows(searchQuery).collect { data ->
                _tvShows.postValue(data)
            }
        }
    }
}