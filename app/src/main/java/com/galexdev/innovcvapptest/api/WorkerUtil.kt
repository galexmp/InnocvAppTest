package com.galexdev.innovcvapptest.api

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * Created by GalexMP on 13/02/2022
 */
object WorkerUtil {
    fun scheduleSync(context: Context){
        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorkManager>(1, TimeUnit.HOURS)
            .setConstraints(constrains)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorkManager.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, syncRequest
        )
    }
}