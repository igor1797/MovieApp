package igor.kuridza.dice.movieapp.ui.fragments.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import igor.kuridza.dice.movieapp.common.DEFAULT_LANGUAGE
import igor.kuridza.dice.movieapp.common.TOP_RATED
import igor.kuridza.dice.movieapp.databinding.MoviesFragmentBinding
import igor.kuridza.dice.movieapp.model.movie.Movie
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.adapters.MovieAdapter
import igor.kuridza.dice.movieapp.ui.adapters.MovieClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment(), MovieClickListener {

    private val viewModel: MoviesViewModel by viewModel()
    private val movieAdapter = MovieAdapter(this)
    private lateinit var binding: MoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun handleLoading(){
        //To do
    }

    private fun handleSuccess(movies: List<Movie>){
        movieAdapter.submitList(movies)
        //To do
    }

    override fun onMovieClickListener(movie: Movie) {
        val direction = MoviesFragmentDirections.goToMovieDetailsFragment(movie)
        findNavController().navigate(direction)
    }
}