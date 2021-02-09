package igor.kuridza.dice.movieapp.ui.fragments.home

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.HomeFragmentBinding
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    override fun getLayoutResourceId(): Int = R.layout.home_fragment

    override fun setUpUi() {
        setBottomNav()
        if (needToLogin()) {
            goToAuthenticationScreen()
        }
    }

    private fun goToAuthenticationScreen() {
        findNavController().navigate(R.id.authentication_navigation)
    }

    private fun needToLogin(): Boolean {
        return !viewModel.isUserSkippedLogin() && !viewModel.isUserLoggedIn()
    }

    private fun setBottomNav() {
        val navController: NavController =
            Navigation.findNavController(requireView().findViewById(R.id.bottomNavHostFragment))
        binding.bottomNav.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener { }
        }
    }
}