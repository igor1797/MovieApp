package igor.kuridza.dice.movieapp.ui.fragments.tv_shows

import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.TvShowsFragmentBinding
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.model.tv_show.TvShow
import igor.kuridza.dice.movieapp.ui.adapters.TvShowAdapter
import igor.kuridza.dice.movieapp.ui.adapters.TvShowClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowsFragment : BaseFragment<TvShowsFragmentBinding>(), TvShowClickListener {

    private val viewModel: TvShowsViewModel by viewModel()
    private val tvShowAdapter = TvShowAdapter(this)

    override fun getLayoutResourceId(): Int = R.layout.tv_shows_fragment

    override fun setUpUi() {
        setUpRecycler()
        getTvShowsByType(POPULAR, DEFAULT_LANGUAGE)
        observeTvShows()
    }

    private fun setUpRecycler() {
        binding.tvShowsRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = tvShowAdapter
        }
    }

    private fun getTvShowsByType(tvShowsType: String, language: String) {
        viewModel.getTvShowsByType(tvShowsType, language)
    }

    private fun observeTvShows() {
        viewModel.tvShows.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Loading -> handleLoading()
                is Error -> handleError(response.message)
                is Success -> handleSuccess(response.data.tvShowList)
            }
        }
    }

    private fun handleError(message: String?) {
        //To do
    }

    private fun handleLoading() {
        binding.apply {
            loading.visible()
            loadingBackground.visible()
        }
    }

    private fun handleSuccess(movies: List<TvShow>) {
        tvShowAdapter.submitList(movies)
        binding.apply {
            loading.gone()
            loadingBackground.gone()
        }
    }

    override fun onTvShowClickListener(tvShow: TvShow, tvShowPosterImage: ImageView) {
        val extras = FragmentNavigatorExtras(
            tvShowPosterImage to tvShow.posterPath!!
        )

        val direction =
            TvShowsFragmentDirections.goToTvShowDetailsFragment(tvShow.id, tvShow.posterPath)
        findNavController().navigate(direction, extras)
    }
}