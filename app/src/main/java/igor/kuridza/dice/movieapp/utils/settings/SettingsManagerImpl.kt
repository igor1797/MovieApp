package igor.kuridza.dice.movieapp.utils.settings

import androidx.appcompat.app.AppCompatDelegate
import igor.kuridza.dice.movieapp.common.DARK_THEME
import igor.kuridza.dice.movieapp.common.LIGHT_THEME
import igor.kuridza.dice.movieapp.common.SYSTEM_DEFAULT_THEME

class SettingsManagerImpl : SettingsManager {

    override fun changeUiMode(uiMode: String) {
        when (uiMode) {
            LIGHT_THEME -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_THEME -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SYSTEM_DEFAULT_THEME -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun setDefaultNightMode(uiMode: Int) {
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }
}