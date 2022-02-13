package com.galexdev.innovcvapptest.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galexdev.innovcvapptest.api.ApiResponseStatus
import com.galexdev.innovcvapptest.data.dataModel.User
import com.galexdev.innovcvapptest.data.database.getDatabase
import kotlinx.coroutines.launch

/**
 * Created by GalexMP on 13/02/2022
 */

private val TAG = UserViewModel::class.java.simpleName
class UserViewModel(private val application: Application, private val id : Long) : ViewModel(){
    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status : LiveData<ApiResponseStatus> get() = _status

    private val _statusAdd = MutableLiveData<ApiResponseStatus>()
    val statusAdd : LiveData<ApiResponseStatus> get() = _statusAdd

    private val _statusDelete = MutableLiveData<ApiResponseStatus>()
    val statusDelete : LiveData<ApiResponseStatus> get() = _statusDelete

    private val _statusUpdate = MutableLiveData<ApiResponseStatus>()
    val statusUpdate : LiveData<ApiResponseStatus> get() = _statusUpdate

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user

    init {
        getUserById(id)
    }

    private fun getUserById(id:Long){
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _user.value = repository.getUserById(id)
                _status.value = ApiResponseStatus.DONE
            }catch (e: Exception){
                _status.value = ApiResponseStatus.ERROR
                Log.d(TAG, "Something was wrong ", e)
            }
        }
    }

    fun createUser(user: User){
        viewModelScope.launch {
            try {
                _statusAdd.value = ApiResponseStatus.LOADING
                repository.createUser(user)
                _statusAdd.value = ApiResponseStatus.DONE
            }catch (e: Exception){
                _statusAdd.value = ApiResponseStatus.ERROR
                Log.d(TAG, "Something was wrong ", e)
            }
        }
    }

    fun deleteUser(id: Long){
        viewModelScope.launch {
            try {
                _statusDelete.value = ApiResponseStatus.LOADING
                repository.deleteUser(id)
                _statusDelete.value = ApiResponseStatus.DONE
            }catch (e: Exception){
                _statusDelete.value = ApiResponseStatus.ERROR
                Log.d(TAG, "Something was wrong ", e)
            }
        }
    }

    fun updateUser(user:User){
        viewModelScope.launch {
            try {
                _statusUpdate.value = ApiResponseStatus.LOADING
                repository.updateUser(user)
                _statusUpdate.value = ApiResponseStatus.DONE
            }catch (e: Exception){
                _statusUpdate.value = ApiResponseStatus.ERROR
                Log.d(TAG, "Something was wrong ", e)
            }
        }
    }

}