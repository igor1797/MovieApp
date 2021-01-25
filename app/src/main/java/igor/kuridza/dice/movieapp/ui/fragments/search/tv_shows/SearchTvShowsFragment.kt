package igor.kuridza.dice.movieapp.ui.fragments.search.tv_shows

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.SearchTvShowsFragmentBinding
import igor.kuridza.dice.movieapp.model.ImageItemType
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShow
import igor.kuridza.dice.movieapp.ui.activities.MainActivity
import igor.kuridza.dice.movieapp.ui.adapters.tv_show.TvShowAdapter
import igor.kuridza.dice.movieapp.ui.adapters.tv_show.TvShowClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel


class SearchTvShowsFragment : BaseFragment<SearchTvShowsFragmentBinding>(), TvShowClickListener {

    private val viewModel: SearchTvShowsViewModel by viewModel()
    private val tvShowAdapter by lazy { TvShowAdapter(this, ImageItemType) }

    override fun getLayoutResourceId(): Int = R.layout.search_tv_shows_fragment

    override fun setUpUi() {
        setUpRecycler()
        setItemColorsOfSearchView()
        setBackNavigationIconOnClickListener()
        setSearchIconOnClickListener()
    }

    private fun setUpRecycler() {
        binding.tvShowsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = tvShowAdapter
        }
    }

    private fun setItemColorsOfSearchView() {
        binding.searchView.setItemsColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun setBackNavigationIconOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setSearchIconOnClickListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchQuery ->
                    handleSearch(searchQuery)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun handleSearch(searchQuery: String) {
        if (searchQuery.isNotEmpty())
            searchMoviesByQuery(searchQuery)
        else
            showSnackbar(getString(R.string.searchQueryInfoMessageText))
    }

    private fun searchMoviesByQuery(query: String) {
        hideErrorMessage()
        hideNoDataMessage()
        viewModel.searchTvShowsByQuery(query, DEFAULT_LANGUAGE)
        viewModel.tvShows.observe(this) {
            when (it) {
                is Success<GetTvShowsResponse> -> handleSuccess(it.data.tvShowList)
                is Error -> handleError(it.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun handleSuccess(data: List<TvShow>?) {
        data?.let { tvShows ->
            hideLoading()
            hideErrorMessage()
            if (tvShows.isNotEmpty()) {
                tvShowAdapter.submitList(tvShows)
                showData()
                hideNoDataMessage()
            } else
                showNoDataMessage()
        }
    }

    private fun handleError(message: String?) {
        hideLoading()
        hideData()
        showErrorMessage()
    }

    private fun handleLoading() {
        hideData()
        hideErrorMessage()
        showLoading()
    }

    private fun showNoDataMessage() {
        binding.noDataMessage.visible()
    }

    private fun hideNoDataMessage() {
        binding.noDataMessage.gone()
    }

    private fun showErrorMessage() {
        binding.errorMessage.visible()
    }

    private fun hideErrorMessage() {
        binding.errorMessage.gone()
    }

    private fun hideData() {
        binding.tvShowsRecyclerView.gone()
    }

    private fun showData() {
        binding.tvShowsRecyclerView.visible()
    }

    private fun hideLoading() {
        binding.progressBar.gone()
    }

    private fun showLoading() {
        binding.progressBar.visible()
    }

    override fun onTvShowClickListener(tvShow: TvShow, tvShowPosterImage: ImageView) {
        val posterPath: String? = tvShow.posterPath
        val extras =
            if (posterPath.isNullOrEmpty()) {
                FragmentNavigatorExtras(tvShowPosterImage to "EmptyOrNullImagePath")
            } else {
                FragmentNavigatorExtras(tvShowPosterImage to posterPath)
            }
        val direction =
            SearchTvShowsFragmentDirections.goToTvShowDetailsFragment(tvShow.id, posterPath ?: "")
        val controller =
            Navigation.findNavController(activity as MainActivity, R.id.mainNavHostFragment)
        controller.navigate(direction, extras)
    }
}