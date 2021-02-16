package igor.kuridza.dice.movieapp.ui.authentication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.auth.LoginFormState
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.repositories.auth.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _requestTokenResponse = MutableLiveData<ViewState<GetRequestToken>>()
    val requestTokenResponse: LiveData<ViewState<GetRequestToken>>
        get() = _requestTokenResponse

    private val _loginRequest = MutableLiveData<ViewState<GetRequestToken>>()
    val loginRequest: LiveData<ViewState<GetRequestToken>>
        get() = _loginRequest

    private val _sessionIdRequest = MutableLiveData<ViewState<GetSessionId>>()
    val sessionIdRequest: LiveData<ViewState<GetSessionId>>
        get() = _sessionIdRequest

    fun createRequestToken() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.createRequestToken().collect { data ->
                _requestTokenResponse.postValue(data)
            }
        }
    }

    fun login(username: String, password: String, requestToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.login(username, password, requestToken).collect { data ->
                _loginRequest.postValue(data)
            }
        }
    }

    fun saveValueInPrefs(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.saveUserSessionId(value)
        }
    }

    fun createSessionId(requestToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.createSessionId(requestToken).collect { data ->
                _sessionIdRequest.postValue(data)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState(usernameError = authenticationRepository.getUserInvalidUsernameStringId())
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(passwordError = authenticationRepository.getInvalidPasswordStringId())
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun userSkippedLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.userSkippedLogin(true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotEmpty()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }
}