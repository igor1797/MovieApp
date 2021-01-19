package igor.kuridza.dice.movieapp.ui.fragments.movies

import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.DEFAULT_LANGUAGE
import igor.kuridza.dice.movieapp.common.TOP_RATED
import igor.kuridza.dice.movieapp.common.gone
import igor.kuridza.dice.movieapp.common.visible
import igor.kuridza.dice.movieapp.databinding.MoviesFragmentBinding
import igor.kuridza.dice.movieapp.model.movie.Movie
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.adapters.MovieAdapter
import igor.kuridza.dice.movieapp.ui.adapters.MovieClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<MoviesFragmentBinding>(), MovieClickListener {

    private val viewModel: MoviesViewModel by viewModel()
    private val movieAdapter = MovieAdapter(this)

    override fun getLayoutResourceId(): Int = R.layout.movies_fragment

    override fun setUpUi() {
        setUpRecycler()
        getMoviesByType(TOP_RATED, DEFAULT_LANGUAGE)
        observeMovies()
    }

    private fun setUpRecycler(){
        binding.moviesRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    private fun getMoviesByType(movieType: String, language: String){
        viewModel.getMoviesByType(movieType, language)
    }

    private fun observeMovies(){
        viewModel.movies.observe(viewLifecycleOwner){ response ->
            when(response){
                is Loading -> handleLoading()
                is Error -> handleError(response.message)
                is Success -> handleSuccess(response.data.movies)
            }
        }
    }

    private fun handleError(message: String?){
        //To do
    }

    private fun handleLoading() {
        binding.apply {
            loading.visible()
            loadingBackground.visible()
        }
    }

    private fun handleSuccess(movies: List<Movie>) {
        movieAdapter.submitList(movies)
        binding.apply {
            loading.gone()
            loadingBackground.gone()
        }
    }

    override fun onMovieClickListener(movie: Movie, moviePosterImage: ImageView) {
        val extras = FragmentNavigatorExtras(
            moviePosterImage to movie.posterPath!!
        )

        val direction =
            MoviesFragmentDirections.goToMovieDetailsFragment(movie.id, movie.posterPath)
        findNavController().navigate(direction, extras)
    }
}