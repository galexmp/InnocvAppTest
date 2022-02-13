package com.galexdev.innovcvapptest.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galexdev.innovcvapptest.data.dataModel.User
import com.galexdev.innovcvapptest.databinding.UserListItemBinding
import com.galexdev.innovcvapptest.utils.getStrDate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by GalexMP on 13/02/2022
 */

private val TAG = UserAdapter::class.java.simpleName

class UserAdapter(private val context: Context) : ListAdapter<User, UserAdapter.UserViewHolder>(
    DiffCallBack
) {

    companion object DiffCallBack : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var onItemClickListener: (User) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    inner class UserViewHolder (private val binding: UserListItemBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(user: User) {
            binding.tvId.text = "ID : ${user.id}"
            binding.tvName.text = "Name: ${user.name}"

            val dateStr = getStrDate(user.birthdate)
            binding.tvBirthdate.text = "Birthday: $dateStr"
            binding.root.setOnClickListener{
                if (::onItemClickListener.isInitialized){
                    onItemClickListener(user)
                }else{
                    Log.e(TAG, "onItemClickListener is not initialized ")
                }
            }
        }
    }
}