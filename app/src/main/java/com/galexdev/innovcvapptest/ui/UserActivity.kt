package com.galexdev.innovcvapptest.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.galexdev.innovcvapptest.R
import com.galexdev.innovcvapptest.api.ApiResponseStatus
import com.galexdev.innovcvapptest.data.dataModel.User
import com.galexdev.innovcvapptest.databinding.ActivityUserBinding
import com.galexdev.innovcvapptest.utils.formatDateToApiDate
import com.galexdev.innovcvapptest.utils.getStrDate
import com.galexdev.innovcvapptest.utils.isValidDate


private val TAG = UserActivity::class.java.simpleName

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: UserViewModel
    private var isEditMode : Boolean = false
    private var isAddUser: Boolean = false
    var id: Long = 0
    private lateinit var mUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getLongExtra("id", 0)
        isAddUser = intent.getBooleanExtra("addUser", false)

        supportActionBar?.title = if (isAddUser) "Add User" else "User detail"

        viewModel = UserViewModel(application, id)

        showButtons(isAddUser)

        if (isAddUser){
            isEditMode = false
            enableUI(true)
            viewModel.statusAdd.observe(this, { apiResponseStatus ->
                when (apiResponseStatus) {
                    ApiResponseStatus.LOADING -> {
                        enableUI(false)
                        Toast.makeText(this, "ADDING ", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponseStatus.DONE -> {
                        clearView()
                        enableUI(true)
                        goToMainActivity()
                        Toast.makeText(this, "DONE ", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "ERROR ", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }else{
            enableUI(false)
            viewModel.user.observe(this, { user ->
                if (user != null) {
                    mUser = user
                    binding.tvId.text = "ID: ${user.id.toString()}"
                    binding.etName.setText("Name: ${user.name}")
                    val dateStr = getStrDate(user.birthdate)
                    binding.etBirthdate.setText("Birthdate: $dateStr")
                }

            })

            viewModel.status.observe(this, { apiResponseStatus ->
                when (apiResponseStatus) {
                    ApiResponseStatus.LOADING -> {
                        Toast.makeText(this, "GETTING ", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponseStatus.DONE -> {
                        Toast.makeText(this, "DONE ", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "ERROR ", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            viewModel.statusDelete.observe(this, { apiResponseStatus ->
                when (apiResponseStatus) {
                    ApiResponseStatus.LOADING -> {
                        enableUI(false)
                        Toast.makeText(this, "DELETING ", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponseStatus.DONE -> {
                        clearView()
                        goToMainActivity()
                        Toast.makeText(this, "DONE ", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "ERROR ", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            viewModel.statusUpdate.observe(this, { apiResponseStatus ->
                when (apiResponseStatus) {
                    ApiResponseStatus.LOADING -> {
                        enableUI(false)
                        Toast.makeText(this, "UPDATING ", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponseStatus.DONE -> {
                        isEditMode = false
                        showButtons(false)
                        goToMainActivity()
                        Toast.makeText(this, "DONE ", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "ERROR ", Toast.LENGTH_SHORT).show()
                    }
                }
            })


        }


        binding.btnSave.setOnClickListener {
            saveUser(isEditMode)
        }

        binding.btnEdit.setOnClickListener {
            isEditMode = true
            binding.etName.setText(mUser.name)
            val dateStr = getStrDate(mUser.birthdate)
            binding.etBirthdate.setText(dateStr)
            enableUI(isEditMode)
            showButtons(isEditMode)
        }

        binding.btnDelete.setOnClickListener {
            deleteUser(id)
        }

    }


    private fun enableUI(enable: Boolean) {
        binding.etName.isEnabled = enable
        binding.etBirthdate.isEnabled = enable

        binding.tvId.setHintTextColor(Color.BLACK)
        binding.etName.setHintTextColor(Color.BLACK)
        binding.etBirthdate.setHintTextColor(Color.BLACK)
    }

    private fun showButtons(showButton: Boolean) {
        binding.btnEdit.visibility = if (showButton) View.GONE else View.VISIBLE
        binding.btnDelete.visibility = if (showButton) View.GONE else View.VISIBLE
        binding.btnSave.visibility = if (showButton) View.VISIBLE else View.GONE
    }

    private fun clearView(){
        binding.tvId.text = ""
        binding.etName.setText("")
        binding.etBirthdate.setText("")
    }

    private fun saveUser(isEditMode:Boolean) {
        val name = binding.etName.text.toString().trim()
        val date = binding.etBirthdate.text.toString().trim()
        if (name.isEmpty()) {
            binding.etName.error = "Field required!"
            binding.etName.requestFocus()
            return
        } else {
            binding.etName.error = null
        }

        //TODO check correct date format
        if (date.isEmpty()) {
            binding.etBirthdate.error = "Field required!"
            binding.etBirthdate.requestFocus()
            return
        } else if(!isValidDate(date)){
            binding.etBirthdate.error = "Invalid format date! Please use dd/MM/yyyy"
            binding.etBirthdate.requestFocus()
            return
        }else{
            binding.etBirthdate.error = null
        }

        val user : User
        if (isEditMode){
            user = User(id, name, formatDateToApiDate(date))
            viewModel.updateUser(user)
        }else{
            user = User(0, name, formatDateToApiDate(date))
            viewModel.createUser(user)
        }

    }

    private fun deleteUser(id: Long) {
        viewModel.deleteUser(id)
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}