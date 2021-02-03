package igor.kuridza.dice.movieapp.ui.fragments.home

import androidx.lifecycle.ViewModel
import igor.kuridza.dice.movieapp.prefs.user.UserPrefs

class HomeViewModel(
    private val userPrefs: UserPrefs
) : ViewModel() {

    fun isUserSkippedLogin(): Boolean {
        return userPrefs.isUserSkippedLogin()
    }

    fun isUserLoggedIn(): Boolean {
        return userPrefs.get().isNotEmpty()
    }
}