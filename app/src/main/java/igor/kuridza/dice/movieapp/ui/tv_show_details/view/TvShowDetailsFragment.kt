package igor.kuridza.dice.movieapp.ui.tv_show_details.view

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.common.utils.showDialog
import igor.kuridza.dice.movieapp.databinding.TvShowDetailsFragmentBinding
import igor.kuridza.dice.movieapp.model.person.Person
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.view_state.Error
import igor.kuridza.dice.movieapp.model.view_state.Loading
import igor.kuridza.dice.movieapp.model.view_state.Success
import igor.kuridza.dice.movieapp.ui.base.fragment.BaseFragment
import igor.kuridza.dice.movieapp.ui.home.activity.view.MainActivity
import igor.kuridza.dice.movieapp.ui.movie_details.AppBarStateChangeListener
import igor.kuridza.dice.movieapp.ui.movie_details.adapter.PersonAdapter
import igor.kuridza.dice.movieapp.ui.movie_details.adapter.PersonClickListener
import igor.kuridza.dice.movieapp.ui.tv_show_details.presentation.TvShowDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowDetailsFragment : BaseFragment<TvShowDetailsFragmentBinding>(), PersonClickListener {

    private val viewModel: TvShowDetailsViewModel by viewModel()
    private val args: TvShowDetailsFragmentArgs by navArgs()
    private val personAdapter by lazy { PersonAdapter(this) }
    private lateinit var appBarStateChangeListener: AppBarStateChangeListener

    override fun getLayoutResourceId(): Int = R.layout.tv_show_details_fragment

    override fun setUpUi() {
        getPrimaryInformationAboutTvShow(args.tvShowId)
        setUpRecycler()
        observeMovieDetails()
        setPosterImageTransition()
        setAppBarLayoutOnStateChangedListener()
        setBackNavigationIconOnClickListener()
        setTvShowPosterOnClickListener()
        observeAccountState()
        setStartOnClickListener()
        listenRatingValue()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun listenRatingValue() {
        val navController = findNavController()
        val navBackStackEntry = navController.getBackStackEntry(R.id.tvShowDetailsFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains("RATING")) {
                val result = navBackStackEntry.savedStateHandle.get<String>("RATING");
                binding.userTvShowRating.text = result
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }

    private fun setUpRecycler() {
        binding.actorsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = personAdapter
        }
    }

    private fun setPosterImageTransition() {
        binding.tvShowPoster.apply {
            val posterPath = args.tvShowPosterPath
            transitionName = posterPath
            loadImage(posterPath)
        }
    }

    private fun observeAccountState() {
        viewModel.accountState.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success<AccountStatesResponse> -> handleUserScoreResponseSuccess(response.data.rated.value)
                is Error -> handleUserScoreResponseError(response.message)
                Loading -> handleUserScoreLoading()
            }
        }
    }

    private fun handleUserScoreResponseError(message: String?) {
        binding.userScoreLoading.gone()
    }

    private fun handleUserScoreResponseSuccess(ratedValue: Number) {
        binding.apply {
            userScoreLoading.gone()
            userTvShowRating.text = ratedValue.toString()
        }
    }

    private fun handleUserScoreLoading() {
        binding.userScoreLoading.visible()
    }

    private fun setStartOnClickListener() {
        binding.star.setOnClickListener {
            if (!viewModel.isUserLoggedIn()) {
                showPopUpDialog()
            } else {
                findNavController().navigate(
                    TvShowDetailsFragmentDirections.goToRateDialogFragment(
                        args.tvShowId,
                        TV_SHOW_TYPE
                    )
                )
            }
        }
    }

    private fun showPopUpDialog() {
        showDialog(
            activity,
            getString(R.string.loginPopUpTitleMessageText),
            R.string.loginText,
            { onLoginClicked() },
            R.string.cancelText,
        )
    }


    private fun onLoginClicked() {
        viewModel.onLoginClicked()
        val controller =
            Navigation.findNavController(activity as MainActivity, R.id.mainNavHostFragment)
        controller.navigate(R.id.authentication_navigation)
    }

    private fun getPrimaryInformationAboutTvShow(tvShowId: Int) {
        viewModel.getPrimaryInformationAboutTvShow(tvShowId)
    }

    private fun initAppBarStateChangeListener() {
        appBarStateChangeListener = object : AppBarStateChangeListener() {
            override fun onStateChanged(state: State) {
                when (state) {
                    State.COLLAPSED -> binding.toolbarTitle.visible()
                    State.EXPANDED -> binding.toolbarTitle.gone()
                    State.IDLE -> binding.toolbarTitle.gone()
                }
            }
        }
    }

    private fun setAppBarLayoutOnStateChangedListener() {
        initAppBarStateChangeListener()
        binding.appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener)
    }

    private fun setBackNavigationIconOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setTvShowPosterOnClickListener() {
        binding.tvShowPoster.setOnClickListener {
            showTvShowPosterImages(args.tvShowId)
        }
    }

    private fun showTvShowPosterImages(tvShowId: Int) {
        val action = TvShowDetailsFragmentDirections.goToImageFragment(tvShowId, TV_SHOW)
        findNavController().navigate(action)
    }

    private fun observeMovieDetails() {
        viewModel.tvShowDetails.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success -> handleSuccess(response.data)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
        viewModel.tvShowCredits.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success -> handleCastSuccess(response.data.cast)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun handleCastSuccess(persons: List<Person>) {
        personAdapter.submitList(persons)
        binding.loading.gone()
    }

    private fun handleSuccess(_tvShowDetails: TvShowDetails) {
        binding.apply {
            tvShowDetails = _tvShowDetails
            loading.gone()
        }
    }

    private fun handleError(message: String?) {

    }

    private fun handleLoading() {
        binding.loading.visible()
    }

    override fun onPersonClickListener(person: Person) {
        val action = TvShowDetailsFragmentDirections.goToPersonWikipediaInfoFragment(person)
        findNavController().navigate(action)
    }
}