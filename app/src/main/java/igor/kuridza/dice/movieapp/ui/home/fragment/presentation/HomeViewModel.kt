package igor.kuridza.dice.movieapp.ui.home.fragment.presentation

import androidx.lifecycle.ViewModel
import igor.kuridza.dice.movieapp.repositories.auth.AuthenticationRepository

class HomeViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun isUserSkippedLogin(): Boolean = authenticationRepository.isUserSkippedLogin()

    fun isUserLoggedIn(): Boolean = authenticationRepository.isUserLoggedIn()
}