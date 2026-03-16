package com.example.menuapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WhatsappActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_whatsapp)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.whatsappRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val phone = findViewById<EditText>(R.id.etPhone)
        val message = findViewById<EditText>(R.id.etMessage)
        val button = findViewById<Button>(R.id.btnSend)

        button.setOnClickListener {
            val number = phone.text.toString().trim().replace(" ", "")
            val msg = message.text.toString().trim()

            if (number.isEmpty()) {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (msg.isEmpty()) {
                Toast.makeText(this, "Enter message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val words = msg.split("\\s+".toRegex()).filter { it.isNotBlank() }
            if (words.size > 200) {
                Toast.makeText(this, "Message should be less than 200 words", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val encodedMessage = Uri.encode(msg)
            val normalizedNumber = if (number.startsWith("+")) number.substring(1) else number
            val url = "https://api.whatsapp.com/send?phone=$normalizedNumber&text=$encodedMessage"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

    }

}