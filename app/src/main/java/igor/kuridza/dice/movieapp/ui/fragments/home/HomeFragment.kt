package igor.kuridza.dice.movieapp.ui.fragments.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.HomeFragmentBinding
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override fun getLayoutResourceId(): Int = R.layout.home_fragment

    override fun setUpUi() {
        if (savedInstanceState == null) {
            setBottomNav()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setBottomNav()
    }

    private fun setBottomNav() {
        val navController: NavController =
            Navigation.findNavController(requireView().findViewById(R.id.bottomNavHostFragment))
        binding.bottomNav.setupWithNavController(navController)
    }
}