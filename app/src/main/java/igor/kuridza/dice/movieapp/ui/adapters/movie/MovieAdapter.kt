package igor.kuridza.dice.movieapp.ui.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.MovieItemWithPosterAndTitleBinding
import igor.kuridza.dice.movieapp.databinding.MovieItemWithTitlePosterAndVoteAverageBinding
import igor.kuridza.dice.movieapp.model.DetailsItemType
import igor.kuridza.dice.movieapp.model.ImageItemType
import igor.kuridza.dice.movieapp.model.RecyclerItemType
import igor.kuridza.dice.movieapp.model.movie.Movie
import igor.kuridza.dice.movieapp.ui.adapters.base.BaseHolder

class MovieAdapter(
    private val movieClickListener: MovieClickListener,
    private val recyclerItemType: RecyclerItemType
) : ListAdapter<Movie, BaseHolder<*, *>>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<*, *> {
        val inflater = LayoutInflater.from(parent.context)
        return when (recyclerItemType) {
            DetailsItemType -> {
                val binding =
                    DataBindingUtil.inflate<MovieItemWithTitlePosterAndVoteAverageBinding>(
                        inflater,
                        R.layout.movie_item_with_title_poster_and_vote_average,
                        parent,
                        false
                    )
                MovieHolder(binding, movieClickListener)
            }
            ImageItemType -> {
                val binding = DataBindingUtil.inflate<MovieItemWithPosterAndTitleBinding>(
                    inflater,
                    R.layout.movie_item_with_poster_and_title,
                    parent,
                    false
                )
                MovieImageHolder(binding, movieClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseHolder<*, *>, position: Int) {
        getItem(position)?.let { movie ->
            when (holder) {
                is MovieHolder -> holder.bindItem(movie)
                is MovieImageHolder -> holder.bindItem(movie)
            }
        }
    }

    inner class MovieHolder(
        private val binding: MovieItemWithTitlePosterAndVoteAverageBinding,
        private val movieClickListener: MovieClickListener
    ) : BaseHolder<Movie, MovieItemWithTitlePosterAndVoteAverageBinding>(binding) {

        override fun bindItem(item: Movie) {
            binding.movie = item
            binding.moviePoster.transitionName = item.posterPath
            binding.root.setOnClickListener {
                movieClickListener.onMovieClickListener(item, binding.moviePoster)
            }
        }
    }

    inner class MovieImageHolder(
        private val binding: MovieItemWithPosterAndTitleBinding,
        private val movieClickListener: MovieClickListener
    ) : BaseHolder<Movie, MovieItemWithPosterAndTitleBinding>(binding) {

        override fun bindItem(item: Movie) {
            binding.movie = item
            val posterPath: String? = item.posterPath
            if (posterPath.isNullOrEmpty()) {
                binding.movieImage.transitionName = "EmptyOrNullImagePath"
            } else {
                binding.movieImage.transitionName = item.posterPath
            }
            binding.root.setOnClickListener {
                movieClickListener.onMovieClickListener(item, binding.movieImage)
            }
        }
    }

    companion object {
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