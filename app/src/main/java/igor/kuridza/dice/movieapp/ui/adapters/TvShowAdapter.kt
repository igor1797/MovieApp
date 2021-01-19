package igor.kuridza.dice.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.TvShowItemBinding
import igor.kuridza.dice.movieapp.model.tv_show.TvShow

class TvShowAdapter(private val tvShowClickListener: TvShowClickListener) :
    ListAdapter<TvShow, TvShowAdapter.TvShowHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<TvShowItemBinding>(
            inflater,
            R.layout.tv_show_item,
            parent,
            false
        )
        return TvShowHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        getItem(position)?.let { tvShow ->
            holder.bindItem(tvShow, tvShowClickListener)
        }
    }

    inner class TvShowHolder(private val binding: TvShowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(tvShow: TvShow, tvShowClickListener: TvShowClickListener) {
            binding.tvShow = tvShow
            binding.tvShowPoster.transitionName = tvShow.posterPath
            binding.root.setOnClickListener {
                tvShowClickListener.onTvShowClickListener(tvShow, binding.tvShowPoster)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}