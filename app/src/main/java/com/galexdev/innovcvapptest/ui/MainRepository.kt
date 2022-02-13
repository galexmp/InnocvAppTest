package com.galexdev.innovcvapptest.ui

import com.galexdev.innovcvapptest.api.UserJsonResponse
import com.galexdev.innovcvapptest.api.service
import com.galexdev.innovcvapptest.data.dataModel.User
import com.galexdev.innovcvapptest.data.database.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by GalexMP on 13/02/2022
 */
class MainRepository(private val database: UserDatabase) {

    suspend fun getUsers() : MutableList<User> {
        return withContext(Dispatchers.IO){
            var userList = mutableListOf<User>()
            userList =  service.getUsers()
            database.userDao.insertAll(userList)
            database.userDao.getUsers()
            //return@withContext userList
        }
    }

    suspend fun getUsersFromDB(): MutableList<User>{
        return withContext(Dispatchers.IO){
            database.userDao.getUsers()
        }
    }

    //TODO insert/get/delete/update on local DB, now is all from API
    suspend fun getUserById(id: Long): User{
        return withContext(Dispatchers.IO){
            //database.userDao.getUserById(id)
            service.getUserById(id)
        }
    }

    suspend fun createUser(user: User){
        withContext(Dispatchers.IO){
            service.createUser(user)
        }
    }

    suspend fun deleteUser(id:Long){
        withContext(Dispatchers.IO  ){
            service.deleteUserById(id)
        }
    }

    suspend fun updateUser(user: User){
        withContext(Dispatchers.IO){
            service.updateUser(user)
        }
    }

}