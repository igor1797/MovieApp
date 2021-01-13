package igor.kuridza.dice.movieapp.ui.fragments.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val mMovies = MutableLiveData<Resource<GetMoviesResponse>>()
    val movies: LiveData<Resource<GetMoviesResponse>>
        get() = mMovies

    fun getMoviesByType(type: String, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMoviesByType(type, language).collect { data ->
                mMovies.postValue(data)
            }
        }
    }

    fun getListOfMoviesForMovie(movieId: Number, movieListType: String, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getListOfMoviesForMovie(movieId, movieListType, language)
                .collect { data ->
                    mMovies.postValue(data)
                }
        }
    }
}