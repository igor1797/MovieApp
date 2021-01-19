package igor.kuridza.dice.movieapp.ui.adapters

import android.widget.ImageView
import igor.kuridza.dice.movieapp.model.movie.Movie

interface MovieClickListener {
    fun onMovieClickListener(movie: Movie, moviePosterImage: ImageView)
}