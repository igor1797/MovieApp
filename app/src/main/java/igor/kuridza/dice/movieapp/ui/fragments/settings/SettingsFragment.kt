package igor.kuridza.dice.movieapp.ui.fragments.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.utils.SettingsManager
import org.koin.android.ext.android.inject

class SettingsFragment : PreferenceFragmentCompat() {

    private val settingsManager: SettingsManager by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setThemeSelectionOnChangeListener()
    }

    private fun setThemeSelectionOnChangeListener() {
        findPreference<ListPreference>("theme")?.setOnPreferenceChangeListener { _, newValue ->
            settingsManager.changeUiMode(newValue.toString())
            //When the same theme is selected again, the activity is recreated again. That needs to be fixed
            activity?.recreate()
            false
        }
    }
}