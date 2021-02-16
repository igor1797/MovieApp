package igor.kuridza.dice.movieapp.ui.poster_images.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.ImageItemBinding
import igor.kuridza.dice.movieapp.model.image.Image

class ImageViewPagerAdapter : RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewPagerHolder>() {

    private val images = arrayListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewPagerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ImageItemBinding>(inflater, R.layout.image_item, parent, false)
        return ImageViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewPagerHolder, position: Int) {
        val image = images[position]
        holder.bindItem(image)
    }

    override fun getItemCount(): Int = images.size

    fun setImages(images: List<Image>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    inner class ImageViewPagerHolder(private val bindingItem: ImageItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {

        fun bindItem(image: Image) {
            bindingItem.image = image
        }
    }
}