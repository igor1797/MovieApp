package igor.kuridza.dice.movieapp.ui.adapters

import igor.kuridza.dice.movieapp.model.movie.Movie

interface MovieClickListener {
    fun onMovieClickListener(movie: Movie)
}