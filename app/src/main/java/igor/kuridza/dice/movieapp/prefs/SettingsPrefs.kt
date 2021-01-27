package igor.kuridza.dice.movieapp.prefs

interface SettingsPrefs {
    fun getTheme(): String?
    fun getLanguage(): String
}