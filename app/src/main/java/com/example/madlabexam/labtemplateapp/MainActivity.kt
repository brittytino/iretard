package com.example.labtemplateapp

import com.example.madlabexam.R
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Links to activity_main.xml

        // 1. Find all our buttons by their IDs
        // TODO: Change these if you rename buttons in activity_main.xml
        val btnAction1 = findViewById<Button>(R.id.btnNavAction1)
        val btnAction2 = findViewById<Button>(R.id.btnNavAction2)
        val btnAction3 = findViewById<Button>(R.id.btnNavAction3)
        val btnAction4 = findViewById<Button>(R.id.btnNavAction4)

        // 2. Load the first fragment by default when app opens
        if (savedInstanceState == null) {
            // TODO: Change this to whichever Fragment should show first
            loadFragment(NotificationReminderFragment())
        }

        // 3. Set click listeners for each button to load different fragments
        btnAction1.setOnClickListener {
            // TODO: Replace with your Fragment
            loadFragment(NotificationReminderFragment())
        }

        btnAction2.setOnClickListener {
            // TODO: Replace with your Fragment
            loadFragment(DialerFragment())
        }

        btnAction3.setOnClickListener {
            // TODO: Replace with your Fragment
            loadFragment(GradeDescFragment())
        }

        btnAction4.setOnClickListener {
            // TODO: Replace with your Fragment
            loadFragment(MessageBroadcastFragment())
        }
    }

    // Helper function to easily switch fragments
    // You do NOT need to change this function.
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentBox, fragment) // R.id.fragmentBox is the FrameLayout in activity_main.xml
            .commit()
    }
}
