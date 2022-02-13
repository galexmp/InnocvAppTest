package com.galexdev.innovcvapptest.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.galexdev.innovcvapptest.ui.UserActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by GalexMP on 13/02/2022
 */
private val TAG = "Utils"
fun getStrDate(date: String): String {
    var dateStr: String = ""
    dateStr = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val localDateTime = LocalDateTime.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        formatter.format(localDateTime)
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        formatter.format(parser.parse(date))
    }

    return dateStr
}

@SuppressLint("SimpleDateFormat")
fun formatDateToApiDate(date: String): String {
    var dateStr: String = ""
    val parser = SimpleDateFormat("dd/MM/yyyy")
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    val dateTime: Date?
    try {
        dateTime = parser.parse(date)
        dateStr = formatter.format(dateTime)
    }catch (ex: ParseException){
        Log.d("TAG", "formatDateToApiDate: "+ex.localizedMessage)
    }

    return dateStr

}

//TODO look for the correct way to check date format
fun isValidDate(date: String): Boolean{
    var isValid: Boolean = false

    var dateStr: String = ""
    val parser = SimpleDateFormat("dd/MM/yyyy")
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    val dateTime: Date?
    try {
        dateTime = parser.parse(date)
        //dateStr = formatter.format(dateTime)
        isValid = true
    }catch (ex: ParseException){
        isValid = false
        Log.d("TAG", "formatDateToApiDate: "+ex.localizedMessage)
    }

    return isValid

}