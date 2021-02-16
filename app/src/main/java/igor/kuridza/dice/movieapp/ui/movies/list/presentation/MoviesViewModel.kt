package igor.kuridza.dice.movieapp.ui.movies.list.presentation

import androidx.lifecycle.*
import igor.kuridza.dice.movieapp.common.POPULAR
import igor.kuridza.dice.movieapp.common.TOP_RATED
import igor.kuridza.dice.movieapp.common.UPCOMING
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val categories = mapOf(
        TOP_RATED to movieRepository.getTopRatedString(),
        POPULAR to movieRepository.getPopularString(),
        UPCOMING to movieRepository.getUpcomingString()
    )

    fun getCategoryNameByKey(category: String): String {
        return categories[category].toString()
    }

    private val _category = MutableLiveData(TOP_RATED)
    val category: LiveData<String>
        get() = _category

    private val mMovies = MutableLiveData<ViewState<GetMoviesResponse>>()
    val movies: LiveData<ViewState<GetMoviesResponse>>
        get() = mMovies

    fun getMoviesByType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMoviesByType(type).collect { data ->
                mMovies.postValue(data)
            }
        }
    }

    fun changeCategory(movieCategory: String) {
        _category.value = movieCategory
    }
}