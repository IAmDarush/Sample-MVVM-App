package com.simpleapp.challenge.ui.userlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simpleapp.challenge.data.model.UserDetails
import com.simpleapp.challenge.databinding.ItemUserListBinding
import com.simpleapp.challenge.ui.base.BaseViewHolder

class UserListRecyclerAdapter: RecyclerView.Adapter<UserListRecyclerAdapter.ViewHolder>() {

  private val items: MutableList<UserDetails> = mutableListOf()

  @SuppressLint("NotifyDataSetChanged")
  fun updateItemsList(newItems: List<UserDetails>) {
    items.clear()
    items.addAll(newItems)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemUserListBinding.inflate(inflater, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(position)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  inner class ViewHolder(private val binding: ItemUserListBinding): BaseViewHolder(binding.root) {

    override fun clear() {
      binding.tvName.text = ""
      binding.tvEmail.text = ""
    }

    override fun onBind(position: Int) {
      super.onBind(position)

      val user = items[position]

      binding.tvName.text = user.name
      binding.tvEmail.text = user.email

    }

  }

}