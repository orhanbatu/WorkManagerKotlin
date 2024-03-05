package com.orhanbatu.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("intKey", 1).build()

        val constraints = Constraints.Builder().setRequiresCharging(false).build()

        /*val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
                                            .setConstraints(constraints)
                                            .setInputData(data)
                                            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)*/

        val myWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15, TimeUnit.MINUTES)
            .setConstraints(constraints).setInputData(data).build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this) {

            if (it.state == WorkInfo.State.RUNNING) {
                println("Running")
            } else if (it.state == WorkInfo.State.FAILED) {
                println("Failed")
            } else if (it.state == WorkInfo.State.SUCCEEDED) {
                println("Succeeded")
            }

        }

        /*val oneTimeRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
                                            .setConstraints(constraints).setInputData(data).build()

        WorkManager.getInstance(this)
            .beginWith(oneTimeRequest)
            .then(oneTimeRequest)
            .then(oneTimeRequest)
            .enqueue()*/
    }
}