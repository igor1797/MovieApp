package igor.kuridza.dice.movieapp.ui.fragments.person

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.PersonWikipediaInfoFragmentBinding
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment

class PersonWikipediaInfoFragment : BaseFragment<PersonWikipediaInfoFragmentBinding>() {

    private val args: PersonWikipediaInfoFragmentArgs by navArgs()

    override fun getLayoutResourceId(): Int = R.layout.person_wikipedia_info_fragment

    override fun setUpUi() {
        binding.person = args.person
        setBackNavigationIconOnClickListener()
    }

    private fun setBackNavigationIconOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}