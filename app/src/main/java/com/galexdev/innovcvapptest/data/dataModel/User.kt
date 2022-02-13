package com.galexdev.innovcvapptest.data.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by GalexMP on 13/02/2022
 */

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Long,
    val name : String,
    val birthdate : String
) {
}