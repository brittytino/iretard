package com.example.madlabexam.ps5

import com.example.madlabexam.R

import android.os.Bundle
import android.widget.TextView
import com.example.madlabexam.BaseActivity

class StudentResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_result)

        findViewById<TextView>(R.id.tvStudentData).text = intent.getStringExtra("student_data") ?: "No data"
    }
}
