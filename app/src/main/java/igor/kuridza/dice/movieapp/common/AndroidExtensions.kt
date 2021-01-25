package igor.kuridza.dice.movieapp.common

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

fun Fragment.showSnackbar(message: String) {
    Snackbar.make(this.requireView().rootView, message, Snackbar.LENGTH_SHORT).show()
}
