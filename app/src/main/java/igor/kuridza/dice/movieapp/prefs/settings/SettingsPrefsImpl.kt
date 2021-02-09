package igor.kuridza.dice.movieapp.prefs.settings

import android.content.Context
import androidx.core.os.ConfigurationCompat
import androidx.preference.PreferenceManager
import igor.kuridza.dice.movieapp.common.DEFAULT_LANGUAGE

private const val LANGUAGE_KEY = "language"
private const val THEME_KEY = "theme"
private const val DEFAULT_THEME = "light"

class SettingsPrefsImpl(private val context: Context) : SettingsPrefs {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getLanguage(): String {
        val itNeedsToBeTranslated = sharedPreferences.getBoolean(LANGUAGE_KEY, false)
        val language =
            ConfigurationCompat.getLocales(context.resources.configuration)[0].isO3Language.substring(
                0,
                2
            )
        return if (itNeedsToBeTranslated) {
            language
        } else
            DEFAULT_LANGUAGE
    }

    override fun getTheme(): String? {
        return sharedPreferences.getString(THEME_KEY, DEFAULT_THEME)
    }
}