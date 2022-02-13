package com.galexdev.innovcvapptest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galexdev.innovcvapptest.data.dataModel.User

/**
 * Created by GalexMP on 13/02/2022
 */

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE : UserDatabase

fun getDatabase(context: Context) : UserDatabase{
    synchronized(UserDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java,
            "user_db").build()
        }
        return INSTANCE
    }

}