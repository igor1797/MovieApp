package igor.kuridza.dice.movieapp.ui.fragments.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.prefs.user.UserPrefs
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val userPrefs: UserPrefs
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetails>>()
    val movieDetails: LiveData<Resource<MovieDetails>>
        get() = _movieDetails

    private val _movieCredits = MutableLiveData<Resource<GetCreditsResponse>>()
    val movieCredits: LiveData<Resource<GetCreditsResponse>>
        get() = _movieCredits

    private val _accountState = MutableLiveData<Resource<AccountStatesResponse>>()
    val accountState: LiveData<Resource<AccountStatesResponse>>
        get() = _accountState

    fun isUserLoggedIn(): Boolean {
        return userPrefs.get().isNotEmpty()
    }

    fun onLoginClicked() {
        userPrefs.userSkippedLogin(false)
    }

    fun getPrimaryInformationAboutMovie(movieId: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                movieRepository.getPrimaryInformationAboutMovie(movieId, language).collect { data ->
                    _movieDetails.postValue(data)
                }
            }
            async {
                movieRepository.getCastAndCrewForAMovie(movieId, language).collect { data ->
                    _movieCredits.postValue(data)
                }
            }
            val sessionId = userPrefs.get()
            async {
                movieRepository.getAccountStatesForMovie(movieId, sessionId).collect { data ->
                    _accountState.postValue(data)
                }
            }
        }
    }
}