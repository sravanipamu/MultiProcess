package com.sravani.multipleprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyBackgroundService : Service() {

    val ACTION_COMPLETE = "com.sravani.ACTION_COMPLETE"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            var result = 0
            for (i in 0..9) {
                result += i
                try {
                    Thread.sleep(1000) // Simulate time-consuming work
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            // Send the result back to the main app using Broadcast

            val broadcastIntent: Intent = Intent(ACTION_COMPLETE)
            broadcastIntent.putExtra("result", result)
            sendBroadcast(broadcastIntent) // Send broadcast to main app
        }.start()

        // If the service is killed by the system, don't restart it
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}