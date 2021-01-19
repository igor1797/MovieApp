package igor.kuridza.dice.movieapp.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import igor.kuridza.dice.movieapp.common.gone
import igor.kuridza.dice.movieapp.common.loadImage
import igor.kuridza.dice.movieapp.common.toHoursAndMinutes
import igor.kuridza.dice.movieapp.common.visible
import igor.kuridza.dice.movieapp.model.CreatedBy
import igor.kuridza.dice.movieapp.model.genre.Genre

object DataBindingAdapter {

    @BindingAdapter("imagePath")
    @JvmStatic
    fun loadImage(imageView: ImageView, imagePath: String?) {
        imageView.loadImage(imagePath)
    }

    @BindingAdapter("genres")
    @JvmStatic
    fun showGenres(textView: TextView, genres: List<Genre>?) {
        textView.text = genres?.joinToString(separator = ", ") { genre ->
            genre.name
        } ?: "UNKNOWN GENRES"
    }

    @BindingAdapter("runtime")
    @JvmStatic
    fun showRuntime(textView: TextView, runtime: Int?) {
        textView.text = runtime?.toHoursAndMinutes() ?: "UNKNOWN RUNTIME"
    }

    @BindingAdapter("quoteVisibility")
    @JvmStatic
    fun changeQuoteVisibility(textView: TextView, quote: String?) {
        quote?.let {
            if (it.isNotEmpty())
                textView.visible()
            else {
                textView.gone()
            }
        }
    }

    @BindingAdapter("quoteText")
    @JvmStatic
    fun showQuoteText(textView: TextView, quote: String?) {
        quote?.let {
            if (it.isNotEmpty()) textView.text = quote
        }
    }

    @BindingAdapter("budget")
    @JvmStatic
    fun showBudget(textView: TextView, budget: Int?) {
        textView.text = if (budget != null && budget != 0) "$$budget" else "UNDEFINED"
    }

    @BindingAdapter("adult")
    @JvmStatic
    fun showAdult(textView: TextView, adult: Boolean) {
        textView.text = if (adult) "YES" else "NO"
    }

    @BindingAdapter("createdBy")
    @JvmStatic
    fun showCreatedBy(textView: TextView, directors: List<CreatedBy>?) {
        textView.text =
            if (directors?.isEmpty() == true)
                "UNDEFINED CREATORS"
            else
                directors?.joinToString(separator = ", ") { director ->
                    director.name
                } ?: "UNKNOWN CREATORS"
    }

    @BindingAdapter("episodeRuntime")
    @JvmStatic
    fun showEpisodeRuntime(textView: TextView, episodeRuntime: List<Int>?) {
        textView.text = episodeRuntime?.joinToString(", ") {
            it.toHoursAndMinutes()
        } ?: "UNKNOWN RUNTIME"
    }
}