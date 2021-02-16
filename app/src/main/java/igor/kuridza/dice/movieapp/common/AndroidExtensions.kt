package igor.kuridza.dice.movieapp.common

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
        .load("$IMAGE_BASE_URL_ORIGINAL$imageUrl")
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_broken_image)
        .into(this)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun SearchView.setItemsColor(color: Int) {
    val searchQuery = this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    val closeButton = this.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
    val searchIcon = this.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
    searchIcon.setColorFilter(color)
    closeButton.setColorFilter(color)
    searchQuery.setTextColor(color)
    searchQuery.setHintTextColor(color)
}

fun Fragment.showSnackbar(message: String) {
    Snackbar.make(this.requireView().rootView, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}
