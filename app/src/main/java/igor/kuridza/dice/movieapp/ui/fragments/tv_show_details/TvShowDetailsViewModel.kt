package igor.kuridza.dice.movieapp.ui.fragments.tv_show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
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

    private val _tvShowDetails = MutableLiveData<Resource<TvShowDetails>>()
    val tvShowDetails: LiveData<Resource<TvShowDetails>>
        get() = _tvShowDetails

    private val _tvShowCredits = MutableLiveData<Resource<GetCreditsResponse>>()
    val tvShowCredits: LiveData<Resource<GetCreditsResponse>>
        get() = _tvShowCredits

    private val _accountState = MutableLiveData<Resource<AccountStatesResponse>>()
    val accountState: LiveData<Resource<AccountStatesResponse>>
        get() = _accountState

    fun isUserLoggedIn(): Boolean {
        return authenticationRepository.isUserLoggedIn()
    }

    fun onLoginClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.userSkippedLogin(false)
        }
    }

    fun getPrimaryInformationAboutTvShow(tvShowId: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val primaryTvShowDetailsFlow =
                tvShowRepository.getPrimaryTvShowDetailsById(tvShowId, language)
            val castAndCrewFlow = tvShowRepository.getCastAndCrewForTvShow(tvShowId, language)
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