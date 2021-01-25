package igor.kuridza.dice.movieapp.ui.fragments.search.movies

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.SearchMoviesFragmentBinding
import igor.kuridza.dice.movieapp.model.ImageItemType
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.movie.Movie
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.activities.MainActivity
import igor.kuridza.dice.movieapp.ui.adapters.movie.MovieAdapter
import igor.kuridza.dice.movieapp.ui.adapters.movie.MovieClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMoviesFragment : BaseFragment<SearchMoviesFragmentBinding>(), MovieClickListener {

    private val viewModel: SearchMoviesViewModel by viewModel()
    private val moviesAdapter by lazy { MovieAdapter(this, ImageItemType) }

    override fun getLayoutResourceId(): Int = R.layout.search_movies_fragment

    override fun setUpUi() {
        setUpRecycler()
        setItemColorsOfSearchView()
        setBackNavigationIconOnClickListener()
        setSearchIconOnClickListener()
    }

    private fun setUpRecycler() {
        binding.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesAdapter
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
        viewModel.searchMoviesByQuery(query, DEFAULT_LANGUAGE)
        viewModel.movies.observe(this) {
            when (it) {
                is Success<GetMoviesResponse> -> handleSuccess(it.data.movies)
                is Error -> handleError(it.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun handleSuccess(data: List<Movie>?) {
        data?.let { movies ->
            hideLoading()
            hideErrorMessage()
            if (movies.isNotEmpty()) {
                moviesAdapter.submitList(movies)
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
        binding.moviesRecyclerView.gone()
    }

    private fun showData() {
        binding.moviesRecyclerView.visible()
    }

    private fun hideLoading() {
        binding.progressBar.gone()
    }

    private fun showLoading() {
        binding.progressBar.visible()
    }

    override fun onMovieClickListener(movie: Movie, moviePosterImage: ImageView) {
        val posterPath: String? = movie.posterPath
        val extras =
            if (posterPath.isNullOrEmpty()) {
                FragmentNavigatorExtras(moviePosterImage to "EmptyOrNullImagePath")
            } else {
                FragmentNavigatorExtras(moviePosterImage to posterPath)
            }
        val direction =
            SearchMoviesFragmentDirections.goToMovieDetailsFragment(movie.id, posterPath ?: "")
        val controller =
            Navigation.findNavController(activity as MainActivity, R.id.mainNavHostFragment)
        controller.navigate(direction, extras)
    }
}