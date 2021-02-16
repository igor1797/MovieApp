package igor.kuridza.dice.movieapp.ui.movies.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchMoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val mMovies = MutableLiveData<ViewState<GetMoviesResponse>>()
    val movies: LiveData<ViewState<GetMoviesResponse>>
        get() = mMovies

    fun searchMoviesByQuery(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.searchMovies(searchQuery).collect { data ->
                mMovies.postValue(data)
            }
        }
    }
}