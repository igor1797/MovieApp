package igor.kuridza.dice.movieapp.ui.adapters.tv_show

import android.widget.ImageView
import igor.kuridza.dice.movieapp.model.tv_show.TvShow

interface TvShowClickListener {
    fun onTvShowClickListener(tvShow: TvShow, tvShowPosterImage: ImageView)
}