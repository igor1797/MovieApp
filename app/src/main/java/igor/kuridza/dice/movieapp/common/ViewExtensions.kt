package igor.kuridza.dice.movieapp.common

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView

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