package igor.kuridza.dice.movieapp.common

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this).load(IMAGE_URL + imageUrl).into(this)
}
