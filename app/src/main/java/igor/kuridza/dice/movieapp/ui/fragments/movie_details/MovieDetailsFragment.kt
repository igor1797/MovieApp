package igor.kuridza.dice.movieapp.ui.fragments.movie_details

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.MovieDetailsFragmentBinding
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<MovieDetailsFragmentBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var appBarStateChangeListener: AppBarStateChangeListener

    override fun getLayoutResourceId(): Int = R.layout.movie_details_fragment

    override fun setUpUi() {
        getPrimaryInformationAboutMovie(args.movieId, DEFAULT_LANGUAGE)
        observeMovieDetails()
        setUpTransition()
        setAppBarLayoutOnStateChangedListener()
        setBackNavigationIconOnClickListener()
        setMoviePosterOnClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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

    private fun getPrimaryInformationAboutMovie(movieId: Int, language: String) {
        viewModel.getPrimaryInformationAboutMovie(movieId, language)
    }

    private fun observeMovieDetails() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success -> handleSuccess(response.data)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
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

    override fun onDestroyView() {
        removeStateChangedListenerFromAppBarLayout()
        super.onDestroyView()
    }
}