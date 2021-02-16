package igor.kuridza.dice.movieapp.ui.movie_details.view

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
import igor.kuridza.dice.movieapp.databinding.MovieDetailsFragmentBinding
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.person.Person
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.view_state.Error
import igor.kuridza.dice.movieapp.model.view_state.Loading
import igor.kuridza.dice.movieapp.model.view_state.Success
import igor.kuridza.dice.movieapp.ui.base.fragment.BaseFragment
import igor.kuridza.dice.movieapp.ui.home.activity.view.MainActivity
import igor.kuridza.dice.movieapp.ui.movie_details.AppBarStateChangeListener
import igor.kuridza.dice.movieapp.ui.movie_details.adapter.PersonAdapter
import igor.kuridza.dice.movieapp.ui.movie_details.adapter.PersonClickListener
import igor.kuridza.dice.movieapp.ui.movie_details.presentation.MovieDetailsViewModel
import igor.kuridza.dice.movieapp.ui.tv_show_details.view.TvShowDetailsFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<MovieDetailsFragmentBinding>(), PersonClickListener {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val personAdapter by lazy { PersonAdapter(this) }
    private lateinit var appBarStateChangeListener: AppBarStateChangeListener

    override fun getLayoutResourceId(): Int = R.layout.movie_details_fragment

    override fun setUpUi() {
        getPrimaryInformationAboutMovie(args.movieId)
        setUpRecycler()
        observeMovieDetails()
        setUpTransition()
        setAppBarLayoutOnStateChangedListener()
        setBackNavigationIconOnClickListener()
        setMoviePosterOnClickListener()
        setStartOnClickListener()
        observeAccountState()
        listenRatingValue()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun listenRatingValue() {
        val navController = findNavController()
        val navBackStackEntry = navController.getBackStackEntry(R.id.movieDetailsFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains("RATING")) {
                val result = navBackStackEntry.savedStateHandle.get<String>("RATING");
                binding.userMovieRating.text = result
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
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
            userMovieRating.text = ratedValue.toString()
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
                    MovieDetailsFragmentDirections.goToRateDialogFragment(
                        args.movieId,
                        MOVIE_TYPE
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

    private fun setUpRecycler() {
        binding.actorsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = personAdapter
        }
    }

    private fun showPosterImages(movieId: Int, type: String) {
        val action = MovieDetailsFragmentDirections.goToImageFragment(movieId, type)
        findNavController().navigate(action)
    }

    private fun setMoviePosterOnClickListener() {
        binding.moviePoster.setOnClickListener {
            showPosterImages(args.movieId, MOVIE)
        }
    }

    private fun setBackNavigationIconOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
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

    private fun removeStateChangedListenerFromAppBarLayout() {
        binding.appBarLayout.removeOnOffsetChangedListener(appBarStateChangeListener)
    }

    private fun setUpTransition() {
        binding.moviePoster.apply {
            val posterPath = args.moviePosterPath
            transitionName = posterPath
            loadImage(posterPath)
        }
    }

    private fun getPrimaryInformationAboutMovie(movieId: Int) {
        viewModel.getPrimaryInformationAboutMovie(movieId)
    }

    private fun observeMovieDetails() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success -> handleSuccess(response.data)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
        viewModel.movieCredits.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success -> handlePersonsSuccess(response.data.cast)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun handlePersonsSuccess(persons: List<Person>) {
        personAdapter.submitList(persons)
        binding.loading.gone()
    }

    private fun handleSuccess(_movieDetails: MovieDetails) {
        binding.apply {
            movieDetails = _movieDetails
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

    override fun onDestroyView() {
        removeStateChangedListenerFromAppBarLayout()
        super.onDestroyView()
    }
}