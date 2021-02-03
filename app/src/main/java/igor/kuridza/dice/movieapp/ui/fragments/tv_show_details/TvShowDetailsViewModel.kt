package igor.kuridza.dice.movieapp.ui.fragments.tv_show_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.prefs.user.UserPrefs
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TvShowDetailsViewModel(
    private val tvShowRepository: TvShowRepository,
    private val userPrefs: UserPrefs
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
        return userPrefs.get().isNotEmpty()
    }

    fun onLoginClicked() {
        userPrefs.userSkippedLogin(false)
    }

    fun getPrimaryInformationAboutTvShow(tvShowId: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                tvShowRepository.getPrimaryTvShowDetailsById(tvShowId, language).collect { data ->
                    _tvShowDetails.postValue(data)
                }
            }

            async {
                tvShowRepository.getCastAndCrewForTvShow(tvShowId, language).collect { data ->
                    _tvShowCredits.postValue(data)
                }
            }
            val sessionId = userPrefs.get()
            async {
                tvShowRepository.getAccountStatesForTvShow(tvShowId, sessionId).collect { data ->
                    Log.d("TAGER", "getPrimaryInformationAboutTvShow: $data")
                    _accountState.postValue(data)
                }
            }
        }
    }
}