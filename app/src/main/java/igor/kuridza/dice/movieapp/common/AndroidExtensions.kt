package igor.kuridza.dice.movieapp.common

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import igor.kuridza.dice.movieapp.R

fun ImageView.loadImage(imageUrl: String?) {

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.apply {
        strokeWidth = 5F
        centerRadius = 50F
        start()
    }

    Glide.with(this)
        .load("$IMAGE_URL$imageUrl")
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_broken_image)
        .into(this)
}

fun Int.toHoursAndMinutes(): String {
    val hours = this / 60
    val remainingMinutes = this - hours * 60
    return when {
        hours == 0 -> "${remainingMinutes}m"
        remainingMinutes == 0 -> "${hours}h"
        else -> "${hours}h ${remainingMinutes}m"
    }
}
