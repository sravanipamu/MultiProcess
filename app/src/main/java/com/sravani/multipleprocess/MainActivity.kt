package com.sravani.multipleprocess

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var resultTextView : TextView

    lateinit var resultReceiver: BroadcastReceiver

    val ACTION_COMPLETE = "com.sravani.ACTION_COMPLETE"

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTextView = findViewById(R.id.resultTextView)
        resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                 if(intent?.action.equals(ACTION_COMPLETE)) {
                     val result = intent!!.getIntExtra("result", 0)
                     resultTextView.text = "Result from service: $result"
                 }
            }
        }
        var filter = IntentFilter(ACTION_COMPLETE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(resultReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(resultReceiver, filter)
        }

        // Start the background service
        startService(Intent(this, MyBackgroundService::class.java));
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver when the activity is destroyed
        unregisterReceiver(resultReceiver)
    }
}