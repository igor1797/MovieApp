package igor.kuridza.dice.movieapp.ui.tv_show_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.repositories.auth.AuthenticationRepository
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TvShowDetailsViewModel(
    private val tvShowRepository: TvShowRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _tvShowDetails = MutableLiveData<ViewState<TvShowDetails>>()
    val tvShowDetails: LiveData<ViewState<TvShowDetails>>
        get() = _tvShowDetails

    private val _tvShowCredits = MutableLiveData<ViewState<GetCreditsResponse>>()
    val tvShowCredits: LiveData<ViewState<GetCreditsResponse>>
        get() = _tvShowCredits

    private val _accountState = MutableLiveData<ViewState<AccountStatesResponse>>()
    val accountState: LiveData<ViewState<AccountStatesResponse>>
        get() = _accountState

    fun isUserLoggedIn(): Boolean {
        return authenticationRepository.isUserLoggedIn()
    }

    fun onLoginClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.userSkippedLogin(false)
        }
    }

    fun getPrimaryInformationAboutTvShow(tvShowId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val primaryTvShowDetailsFlow = tvShowRepository.getPrimaryTvShowDetailsById(tvShowId)
            val castAndCrewFlow = tvShowRepository.getCastAndCrewForTvShow(tvShowId)
            primaryTvShowDetailsFlow.collect { primaryTvSHowDetails ->
                _tvShowDetails.postValue(
                    primaryTvSHowDetails
                )
            }
            castAndCrewFlow.collect { castAndCrew -> _tvShowCredits.postValue(castAndCrew) }

            val sessionId = authenticationRepository.getSessionId()
            val accountStatesForTvShowFlow =
                tvShowRepository.getAccountStatesForTvShow(tvShowId, sessionId)
            accountStatesForTvShowFlow.collect { accountState ->
                _accountState.postValue(
                    accountState
                )
            }
        }
    }
}