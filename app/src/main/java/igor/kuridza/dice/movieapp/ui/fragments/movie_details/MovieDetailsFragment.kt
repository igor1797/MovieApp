package igor.kuridza.dice.movieapp.ui.fragments.movie_details

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.MovieDetailsFragmentBinding
import igor.kuridza.dice.movieapp.model.person.Person
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.activities.MainActivity
import igor.kuridza.dice.movieapp.ui.adapters.person.PersonAdapter
import igor.kuridza.dice.movieapp.ui.adapters.person.PersonClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.movieapp.ui.fragments.rating.RateListener
import igor.kuridza.dice.movieapp.ui.fragments.tv_show_details.TvShowDetailsFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<MovieDetailsFragmentBinding>(), PersonClickListener,
    RateListener {

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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

    override fun onRatingChange(ratingValue: Float) {
        binding.userMovieRating.text = ratingValue.toString()
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