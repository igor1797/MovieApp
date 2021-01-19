package igor.kuridza.dice.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.MovieItemBinding
import igor.kuridza.dice.movieapp.model.movie.Movie

class MovieAdapter(private val movieClickListener: MovieClickListener): ListAdapter<Movie, MovieAdapter.MovieHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<MovieItemBinding>(inflater, R.layout.movie_item, parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindItem(movie, movieClickListener)
        }
    }

    inner class MovieHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem(movie: Movie, movieClickListener: MovieClickListener){
            binding.movie = movie
            binding.moviePoster.transitionName = movie.posterPath
            binding.root.setOnClickListener {
                movieClickListener.onMovieClickListener(movie, binding.moviePoster)
            }
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}