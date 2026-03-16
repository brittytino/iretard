package com.example.labtemplateapp

import com.example.madlabexam.R
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class NotificationReminderFragment : Fragment() {

    // 1. Connect Layout XML. TODO: Change R.layout.fragment_notification if you rename XML
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ensure Notification Channel is created (Required for Android 8+)
        NotificationHelper.createNotificationChannel(requireContext())

        // 2. Find Views by ID. TODO: Change IDs if you renamed them in XML
        val editTitle = view.findViewById<EditText>(R.id.editTitle)
        val editMessage = view.findViewById<EditText>(R.id.editMessage)
        val editDelay = view.findViewById<EditText>(R.id.editDelay)
        val btnTriggerAction = view.findViewById<Button>(R.id.btnTriggerAction)

        // 3. Set Button Click
        btnTriggerAction.setOnClickListener {
            // Get text from inputs
            val titleText = editTitle.text.toString()
            val messageText = editMessage.text.toString()
            val delayText = editDelay.text.toString()

            // Basic Error Check
            if (titleText.isEmpty() || delayText.isEmpty()) {
                Toast.makeText(requireContext(), "Fill required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert delay to number
            val delaySeconds = delayText.toLong()

            // Create Intent to trigger AlarmReceiver
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            intent.putExtra("EXTRA_TITLE", titleText)
            intent.putExtra("EXTRA_MESSAGE", messageText)

            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Setup AlarmManager
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val triggerTime = System.currentTimeMillis() + (delaySeconds * 1000)

            // Basic Set Exact Alarm
            // Note: Modern Android needs a permission for exact alarms, which is in AndroidManifest.xml
            try {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                Toast.makeText(requireContext(), "Alarm set for $delaySeconds seconds!", Toast.LENGTH_SHORT).show()
            } catch (e: SecurityException) {
                Toast.makeText(requireContext(), "Permission error setting alarm", Toast.LENGTH_LONG).show()
            }
        }
    }
}
