package igor.kuridza.dice.movieapp.ui.base.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<T, BINDING : ViewDataBinding>(binding: BINDING) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bindItem(item: T)
}