package igor.kuridza.dice.movieapp.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import igor.kuridza.dice.movieapp.common.loadImage

object DataBindingAdapter {

    @BindingAdapter("imagePath")
    @JvmStatic fun loadImage(imageView: ImageView, imagePath: String){
        imageView.loadImage(imagePath)
    }
}