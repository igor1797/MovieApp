package igor.kuridza.dice.movieapp.prefs.user

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private const val PREFS_NAME = "user_prefs"
private const val ENCRYPTED_PREFS_NAME = "encrypted_user_prefs"
private const val KEY = "key"
private const val USER_SKIPPED_LOGIN_KEY = "user_skipped_login_key"

class UserPrefsImpl(applicationContext: Context) : UserPrefs {

    private val sharedPrefs by lazy {
        applicationContext.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )
    }

    private val encryptedSharedPrefs by lazy {
        val masterKeyAlias: MasterKey =
            MasterKey.Builder(applicationContext).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        EncryptedSharedPreferences.create(
            applicationContext,
            ENCRYPTED_PREFS_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun put(value: String) {
        encryptedSharedPrefs.edit().putString(KEY, value).apply()
    }

    override fun get(): String {
        return encryptedSharedPrefs.getString(KEY, "") ?: ""
    }

    override fun userSkippedLogin(userSkipped: Boolean) {
        sharedPrefs.edit().putBoolean(USER_SKIPPED_LOGIN_KEY, userSkipped).apply()
    }

    override fun isUserSkippedLogin(): Boolean {
        return sharedPrefs.getBoolean(USER_SKIPPED_LOGIN_KEY, false)
    }
}