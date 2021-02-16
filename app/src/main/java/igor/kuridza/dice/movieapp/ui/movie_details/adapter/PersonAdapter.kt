package igor.kuridza.dice.movieapp.ui.movie_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.databinding.PersonItemBinding
import igor.kuridza.dice.movieapp.model.person.Person

class PersonAdapter(
    private val personClickListener: PersonClickListener
) : ListAdapter<Person, PersonAdapter.PersonHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<PersonItemBinding>(
            inflater,
            R.layout.person_item,
            parent,
            false
        )
        return PersonHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        getItem(position)?.let { person ->
            holder.bindItem(person, personClickListener)
        }
    }

    inner class PersonHolder(private val binding: PersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(_person: Person, personClickListener: PersonClickListener) {
            binding.apply {
                person = _person
                root.setOnClickListener {
                    personClickListener.onPersonClickListener(_person)
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }
}