package igor.kuridza.dice.movieapp.prefs.user

interface UserPrefs {
    fun put(value: String)
    fun get(): String
    fun userSkippedLogin(userSkipped: Boolean)
    fun isUserSkippedLogin(): Boolean
}