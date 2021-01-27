package igor.kuridza.dice.movieapp.prefs

import android.content.Context
import androidx.preference.PreferenceManager
import igor.kuridza.dice.movieapp.common.CROATIAN_LANGUAGE
import igor.kuridza.dice.movieapp.common.DEFAULT_LANGUAGE

private const val LANGUAGE_KEY = "language"
private const val THEME_KEY = "theme"
private const val DEFAULT_THEME = "light"

class SettingsPrefsImpl(private val context: Context) : SettingsPrefs {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getLanguage(): String {
        val itNeedsToBeTranslated = sharedPreferences.getBoolean(LANGUAGE_KEY, false)
        return if (itNeedsToBeTranslated) {
            CROATIAN_LANGUAGE
        } else
            DEFAULT_LANGUAGE
    }

    override fun getTheme(): String? {
        return sharedPreferences.getString(THEME_KEY, DEFAULT_THEME)
    }
}