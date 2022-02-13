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
import java.net.UnknownHostException

/**
 * Created by GalexMP on 13/02/2022
 */
private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(private val application: Application) :ViewModel(){

    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status : LiveData<ApiResponseStatus> get() = _status

    private val _userList = MutableLiveData<MutableList<User>>()
    val userList : LiveData<MutableList<User>> get() = _userList

    init {
        reloadUsersFromDB()
    }

    fun reloadUsers(){
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _userList.value = repository.getUsers()
                _status.value = ApiResponseStatus.DONE
            }catch (e: UnknownHostException){
                _status.value = ApiResponseStatus.NO_INTERNET_CONNECTION
                Log.d(TAG, "No internet connection", e)
            }
        }
    }

    fun reloadUsersFromDB(){
        viewModelScope.launch {
            _userList.value = repository.getUsersFromDB()
            if (_userList.value!!.isEmpty()){
                reloadUsers()
            }
        }
    }

}