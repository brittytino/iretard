package com.example.labtemplateapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// This runs in the background even if the app UI is closed!
// TODO: Change the class name if requested in the exam
class MessageBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Did we catch our custom action?
        // TODO: Ensure this matches the string in AndroidManifest.xml and the Intent sender
        if (intent.action == "THIS_IS_MY_CUSTOM_ACTION") {
            
            // Get the extra data we attached
            val myData = intent.getStringExtra("MY_SECRET_DATA")
            
            // Show a simple pop-up Toast
            Toast.makeText(context, "BACKGROUND RECEIVER CAUGHT: $myData", Toast.LENGTH_LONG).show()
        }
    }
}
