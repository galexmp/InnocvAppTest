package com.galexdev.innovcvapptest.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.galexdev.innovcvapptest.R
import com.galexdev.innovcvapptest.api.ApiResponseStatus
import com.galexdev.innovcvapptest.api.WorkerUtil
import com.galexdev.innovcvapptest.data.dataModel.User
import com.galexdev.innovcvapptest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel // by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WorkerUtil.scheduleSync(this)

        viewModel = MainViewModel(application)

        binding.userRecycler.layoutManager = LinearLayoutManager(this)

        val adapter = UserAdapter(this)
        binding.userRecycler.adapter = adapter

        viewModel.userList.observe(this, {userList ->
            adapter.submitList(userList)
            handleEmptyView(userList, binding)

        })

        viewModel.status.observe(this,{
            apiResponseStatus ->
            when(apiResponseStatus){
                ApiResponseStatus.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                ApiResponseStatus.DONE -> {
                    binding.progress.visibility = View.GONE
                }
                ApiResponseStatus.NO_INTERNET_CONNECTION -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.no_internet_connection_msg), Toast.LENGTH_SHORT).show()
                }
                else -> {Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()}
            }
        })

        adapter.onItemClickListener = { user ->
            goToUserDetail(user.id, false)
        }

        binding.fabAddUser.setOnClickListener{
            goToUserDetail(0, true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.main_menu_option_refresh){
            Toast.makeText(this, "Updating list", Toast.LENGTH_SHORT).show()
            viewModel.reloadUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToUserDetail(id: Long, addUser : Boolean){
        val userIntent : Intent = Intent(this, UserActivity::class.java)
        userIntent.putExtra("id", id)
        userIntent.putExtra("addUser", addUser)
        startActivity(userIntent)
    }

    private fun handleEmptyView(userList: MutableList<User>, binding: ActivityMainBinding){
        if (userList.isEmpty()){
            binding.tvEmptyView.visibility = View.VISIBLE
        }else{
            binding.tvEmptyView.visibility = View.GONE
        }
    }
}