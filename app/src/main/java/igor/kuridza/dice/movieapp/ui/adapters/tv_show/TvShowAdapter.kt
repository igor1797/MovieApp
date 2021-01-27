package igor.kuridza.dice.movieapp.ui.adapters.tv_show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.TvShowItemWithNamePosterAndVoteAverageBinding
import igor.kuridza.dice.movieapp.databinding.TvShowItemWithPosterAndTitleBinding
import igor.kuridza.dice.movieapp.model.DetailsItemType
import igor.kuridza.dice.movieapp.model.ImageItemType
import igor.kuridza.dice.movieapp.model.RecyclerItemType
import igor.kuridza.dice.movieapp.model.tv_show.TvShow
import igor.kuridza.dice.movieapp.ui.adapters.base.BaseHolder

class TvShowAdapter(
    private val tvShowClickListener: TvShowClickListener,
    private val recyclerItemType: RecyclerItemType
) : ListAdapter<TvShow, BaseHolder<*, *>>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<*, *> {
        val inflater = LayoutInflater.from(parent.context)
        return when (recyclerItemType) {
            DetailsItemType -> {
                val binding =
                    DataBindingUtil.inflate<TvShowItemWithNamePosterAndVoteAverageBinding>(
                        inflater,
                        R.layout.tv_show_item_with_name_poster_and_vote_average,
                        parent,
                        false
                    )
                TvShowHolder(binding, tvShowClickListener)
            }
            ImageItemType -> {
                val binding = DataBindingUtil.inflate<TvShowItemWithPosterAndTitleBinding>(
                    inflater,
                    R.layout.tv_show_item_with_poster_and_title,
                    parent,
                    false
                )
                TvShowImageHolder(binding, tvShowClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseHolder<*, *>, position: Int) {
        getItem(position)?.let { tvShow ->
            when (holder) {
                is TvShowHolder -> holder.bindItem(tvShow)
                is TvShowImageHolder -> holder.bindItem(tvShow)
            }
        }
    }

    inner class TvShowHolder(
        private val binding: TvShowItemWithNamePosterAndVoteAverageBinding,
        private val tvShowClickListener: TvShowClickListener
    ) : BaseHolder<TvShow, TvShowItemWithNamePosterAndVoteAverageBinding>(binding) {
        override fun bindItem(item: TvShow) {
            binding.apply {
                tvShow = item
                tvShowPoster.transitionName = item.posterPath ?: "EmptyOrNullImagePath"
                root.setOnClickListener {
                    tvShowClickListener.onTvShowClickListener(item, binding.tvShowPoster)
                }
            }
        }
    }

    inner class TvShowImageHolder(
        private val binding: TvShowItemWithPosterAndTitleBinding,
        private val tvShowClickListener: TvShowClickListener
    ) : BaseHolder<TvShow, TvShowItemWithPosterAndTitleBinding>(binding) {

        override fun bindItem(item: TvShow) {
            binding.apply {
                tvShow = item
                tvShowImage.transitionName = item.posterPath ?: "EmptyOrNullImagePath"
                root.setOnClickListener {
                    tvShowClickListener.onTvShowClickListener(item, binding.tvShowImage)
                }
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