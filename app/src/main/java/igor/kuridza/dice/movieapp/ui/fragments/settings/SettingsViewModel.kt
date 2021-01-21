package igor.kuridza.dice.movieapp.ui.fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.movieapp.model.theme.Theme

class SettingsViewModel : ViewModel() {

    private val _theme = MutableLiveData<Theme>()
    val theme: LiveData<Theme>
        get() = _theme

    fun onDarkModeClicked() {
        _theme.value = Theme.DARK
    }

    fun onLightThemeClicked() {
        _theme.value = Theme.LIGHT
    }
}