package com.bchmsl.presentation_upd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bchmsl.presentation_upd.databinding.LayoutUserBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    var itemClickListener: ((User) -> Unit)? = null
    var removeClickListener: ((User) -> Unit)? = null

    private var adapterList = listOf<User>()

    inner class UsersViewHolder(private val binding: LayoutUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val currentItem = adapterList[adapterPosition]
            binding.apply {
                tvId.text = currentItem.id.toString()
                tvName.text = currentItem.name
                tvEmail.text = currentItem.email
                root.setOnClickListener { itemClickListener?.invoke(currentItem) }
                btnRemove.setOnClickListener { removeClickListener?.invoke(currentItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = adapterList.size

    fun submitList(newList: List<User>) {
        val callback = UsersCallback(adapterList, newList)
        val result = DiffUtil.calculateDiff(callback)
        adapterList = newList
        result.dispatchUpdatesTo(this@UsersAdapter)
    }
}