package igor.kuridza.dice.movieapp.ui.fragments.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetails>>()
    val movieDetails: LiveData<Resource<MovieDetails>>
        get() = _movieDetails

    private val _movieCredits = MutableLiveData<Resource<GetCreditsResponse>>()
    val movieCredits: LiveData<Resource<GetCreditsResponse>>
        get() = _movieCredits

    fun getPrimaryInformationAboutMovie(movieId: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPrimaryInformationAboutMovie(movieId, language).collect { data ->
                _movieDetails.postValue(data)
            }
            movieRepository.getCastAndCrewForAMovie(movieId, language).collect { data ->
                _movieCredits.postValue(data)
            }
        }
    }
}