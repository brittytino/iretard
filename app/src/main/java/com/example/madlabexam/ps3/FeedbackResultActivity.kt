package com.example.madlabexam.ps3

import com.example.madlabexam.R

import android.os.Bundle
import android.widget.TextView
import com.example.madlabexam.BaseActivity

class FeedbackResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_result)

        val message = intent.getStringExtra("feedback_message") ?: "No feedback"
        findViewById<TextView>(R.id.tvSubmittedMessage).text = message
    }
}
