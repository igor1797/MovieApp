package igor.kuridza.dice.movieapp.ui.fragments.tv_show_details

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.TvShowDetailsFragmentBinding
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.movieapp.ui.fragments.movie_details.AppBarStateChangeListener
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowDetailsFragment : BaseFragment<TvShowDetailsFragmentBinding>() {

    private val viewModel: TvShowDetailsViewModel by viewModel()
    private val args: TvShowDetailsFragmentArgs by navArgs()
    private lateinit var appBarStateChangeListener: AppBarStateChangeListener

    override fun getLayoutResourceId(): Int = R.layout.tv_show_details_fragment

    override fun setUpUi() {
        getPrimaryInformationAboutTvShow(args.tvShowId, DEFAULT_LANGUAGE)
        observeMovieDetails()
        setPosterImageTransition()
        setAppBarLayoutOnStateChangedListener()
        setBackNavigationIconOnClickListener()
        setTvShowPosterOnClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun setPosterImageTransition() {
        binding.tvShowPoster.apply {
            val posterPath = args.tvShowPosterPath
            transitionName = posterPath
            loadImage(posterPath)
        }
    }

    private fun getPrimaryInformationAboutTvShow(tvShowId: Int, language: String) {
        viewModel.getPrimaryInformationAboutTvShow(args.tvShowId, DEFAULT_LANGUAGE)
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
}