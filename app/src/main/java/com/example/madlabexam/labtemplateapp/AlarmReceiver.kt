package com.example.labtemplateapp

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Very basic Alarm Receiver to show a notification when AlarmManager fires it
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Read data passed from AlarmManager
        val myTitle = intent.getStringExtra("EXTRA_TITLE") ?: "Alarm!"
        val myMessage = intent.getStringExtra("EXTRA_MESSAGE") ?: "Time is up!"

        // If the user taps the notification, we just open MainActivity
        val tapIntent = Intent(context, MainActivity::class.java)
        val pendingTapIntent = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // 1. Build the Notification
        // Make sure NotificationHelper.CHANNEL_ID matches the one created in NotificationHelper
        val notificationBuilder = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_email) // Very basic built in icon
            .setContentTitle(myTitle)
            .setContentText(myMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Low/Default priority is easy
            .setContentIntent(pendingTapIntent)
            .setAutoCancel(true) // Clears when clicked

        // 2. Show the Notification
        // Check for Android 13 POST_NOTIFICATIONS permission simply (no crash if missing)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // No permission, just silently ignore. (Exam tip: add permission in Manifest!)
            return
        }

        // Send the notification to the screen
        NotificationManagerCompat.from(context).notify(1234, notificationBuilder.build())
    }
}
