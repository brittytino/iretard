package com.example.labtemplateapp

import com.example.madlabexam.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MessageBroadcastFragment : Fragment() {

    // A simple internal receiver just to show text on the screen when broadcast happens
    private val myLocalReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Get data from the broadcast
            val incomingMessage = intent?.getStringExtra("MY_SECRET_DATA") ?: "No Data"
            
            // Just update the TextView on screen!
            view?.findViewById<TextView>(R.id.textBroadcastStatus)?.text = "Received!\nMessage: $incomingMessage"
        }
    }

    // 1. Connect Layout XML. TODO: Change R.layout.fragment_message if you rename XML
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Find Views by ID
        val btnSendBroadcast = view.findViewById<Button>(R.id.btnSendBroadcast)

        // 3. Set Button Click
        btnSendBroadcast.setOnClickListener {
            // Create a custom Intent saying "THIS_IS_MY_CUSTOM_ACTION"
            // TODO: Rename this action string to whatever the exam asks
            val myIntent = Intent("THIS_IS_MY_CUSTOM_ACTION")
            
            // Add some custom data to it
            myIntent.putExtra("MY_SECRET_DATA", "Hello world from the button!")
            
            // If we are targeting modern Android, we might need to tell it to only go to our app
            myIntent.setPackage(requireContext().packageName)
            
            // Fire the broadcast!
            requireContext().sendBroadcast(myIntent)
            
            Toast.makeText(requireContext(), "Broadcast fired!", Toast.LENGTH_SHORT).show()
        }
    }

    // Always register our receiver when fragment starts showing
    override fun onResume() {
        super.onResume()
        // We tell our app: "Listen for ANY broadcast that matches 'THIS_IS_MY_CUSTOM_ACTION'"
        val filter = IntentFilter("THIS_IS_MY_CUSTOM_ACTION")
        
        // This if-else is just to handle old and new Android safely. Don't worry about it too much.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().registerReceiver(myLocalReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            requireContext().registerReceiver(myLocalReceiver, filter)
        }
    }

    // Always stop listening when fragment goes away to save battery
    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(myLocalReceiver)
    }
}
