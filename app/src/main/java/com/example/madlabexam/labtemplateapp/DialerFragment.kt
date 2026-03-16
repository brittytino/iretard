package com.example.labtemplateapp

import com.example.madlabexam.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class DialerFragment : Fragment() {

    // 1. Connect Layout XML. TODO: Change R.layout.fragment_dialer if you rename XML
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Find Views by ID. TODO: Change IDs if you renamed them in XML
        val editPhone = view.findViewById<EditText>(R.id.editPhone)
        val btnCall = view.findViewById<Button>(R.id.btnCall)

        // 3. Set Button Click
        btnCall.setOnClickListener {
            // Get text from input
            val phoneNumberText = editPhone.text.toString()

            // Basic Error Check
            if (phoneNumberText.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ACTION_DIAL opens the dialer application with the number pre-filled.
            // It DOES NOT require the CALL_PHONE permission! Easy exam points.
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$phoneNumberText")
            startActivity(dialIntent)
        }
    }
}
