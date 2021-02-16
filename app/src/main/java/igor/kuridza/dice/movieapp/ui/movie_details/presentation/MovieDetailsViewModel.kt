package igor.kuridza.dice.movieapp.ui.movie_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.repositories.auth.AuthenticationRepository
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<ViewState<MovieDetails>>()
    val movieDetails: LiveData<ViewState<MovieDetails>>
        get() = _movieDetails

    private val _movieCredits = MutableLiveData<ViewState<GetCreditsResponse>>()
    val movieCredits: LiveData<ViewState<GetCreditsResponse>>
        get() = _movieCredits

    private val _accountState = MutableLiveData<ViewState<AccountStatesResponse>>()
    val accountState: LiveData<ViewState<AccountStatesResponse>>
        get() = _accountState

    val rating = MutableLiveData<Float>()

    fun isUserLoggedIn(): Boolean {
        return authenticationRepository.isUserLoggedIn()
    }

    fun onLoginClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.userSkippedLogin(false)
        }
    }

    fun getPrimaryInformationAboutMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val informationAboutMovieFlow = movieRepository.getPrimaryInformationAboutMovie(movieId)
            val castAndCrewForAMovieFlow = movieRepository.getCastAndCrewForAMovie(movieId)

            informationAboutMovieFlow.collect { movieDetails ->
                _movieDetails.postValue(movieDetails)
            }
            castAndCrewForAMovieFlow.collect { castAndCrewForAMovie ->
                _movieCredits.postValue(castAndCrewForAMovie)
            }

            val sessionId = authenticationRepository.getSessionId()
            val accountStatesFlow = movieRepository.getAccountStatesForMovie(movieId, sessionId)
            accountStatesFlow.collect { accountStates -> _accountState.postValue(accountStates) }
        }
    }
}