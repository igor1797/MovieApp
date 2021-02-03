package igor.kuridza.dice.movieapp.ui.adapters.data_binding

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import igor.kuridza.dice.movieapp.common.convertToHoursAndMinutes
import igor.kuridza.dice.movieapp.common.gone
import igor.kuridza.dice.movieapp.common.loadImage
import igor.kuridza.dice.movieapp.common.visible
import igor.kuridza.dice.movieapp.model.person.CreatedBy
import igor.kuridza.dice.movieapp.model.genre.Genre

object DataBindingAdapter {

    private const val UNDEFINED = "N/A"

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
        } ?: UNDEFINED
    }

    @BindingAdapter("person_name")
    @JvmStatic
    fun loadWikipediaUrl(webView: WebView, personName: String?) {
        personName?.let { name ->
            val nameAndSurname = name.split(" ").joinToString("_")
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl("https://en.wikipedia.org/wiki/$nameAndSurname")
            }
        }
    }

    @BindingAdapter("runtime")
    @JvmStatic
    fun showRuntime(textView: TextView, runtime: Int?) {
        textView.text =
            if (runtime == null) UNDEFINED else convertToHoursAndMinutes(runtime)
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
        textView.text = if (budget != null && budget != 0) "$$budget" else UNDEFINED
    }

    @BindingAdapter("adult")
    @JvmStatic
    fun showAdult(textView: TextView, adult: Boolean) {
        textView.text = if (adult) "Yes" else "No"
    }

    @BindingAdapter("createdBy")
    @JvmStatic
    fun showCreatedBy(textView: TextView, directors: List<CreatedBy>?) {
        textView.text =
            if (directors?.isEmpty() == true)
                UNDEFINED
            else
                directors?.joinToString(separator = ", ") { director ->
                    director.name
                } ?: UNDEFINED
    }

    @BindingAdapter("episodeRuntime")
    @JvmStatic
    fun showEpisodeRuntime(textView: TextView, episodeRuntime: List<Int>?) {
        textView.text = episodeRuntime?.joinToString(", ") {
            convertToHoursAndMinutes(it)
        } ?: UNDEFINED
    }
}