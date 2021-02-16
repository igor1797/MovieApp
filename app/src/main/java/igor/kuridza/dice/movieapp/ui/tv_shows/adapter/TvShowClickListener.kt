package igor.kuridza.dice.movieapp.ui.tv_shows.adapter

import android.widget.ImageView
import igor.kuridza.dice.movieapp.model.tv_show.TvShow

interface TvShowClickListener {
    fun onTvShowClickListener(tvShow: TvShow, tvShowPosterImage: ImageView)
}