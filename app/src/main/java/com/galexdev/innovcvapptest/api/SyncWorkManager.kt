package com.galexdev.innovcvapptest.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.galexdev.innovcvapptest.data.database.getDatabase
import com.galexdev.innovcvapptest.ui.MainRepository

/**
 * Created by GalexMP on 13/02/2022
 */
class SyncWorkManager(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    companion object{
        const val WORK_NAME = "SyncWorkManager"
    }

    private val database = getDatabase(context)
    private val repository = MainRepository(database)

    override suspend fun doWork(): Result {
        repository.getUsers()

        return Result.success()
    }


}