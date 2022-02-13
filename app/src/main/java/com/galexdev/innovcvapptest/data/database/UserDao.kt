package com.galexdev.innovcvapptest.data.database

import androidx.room.*
import com.galexdev.innovcvapptest.data.dataModel.User

/**
 * Created by GalexMP on 13/02/2022
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userList: MutableList<User>)

    @Query("SELECT * FROM users")
    fun getUsers() : MutableList<User>

    @Query("SELECT * FROM users where id=:id")
    fun getUserById(id:Long) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUserById(user: User)

}