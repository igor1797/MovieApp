package igor.kuridza.dice.movieapp.ui.fragments.settings

import androidx.appcompat.app.AppCompatDelegate
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.SettingsFragmentBinding
import igor.kuridza.dice.movieapp.model.theme.Theme
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun getLayoutResourceId(): Int = R.layout.settings_fragment

    override fun setUpUi() {
        observeTheme()
        binding.viewModel = viewModel
    }

    private fun observeTheme() {
        viewModel.theme.observe(viewLifecycleOwner) { theme ->
            when (theme) {
                Theme.DARK -> changeThemeToDarkMode()
                Theme.LIGHT -> changeThemeToLightMode()
            }
        }
    }

    private fun changeThemeToDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun changeThemeToLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}