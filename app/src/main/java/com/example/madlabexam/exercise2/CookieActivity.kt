package com.example.madlabexam.exercise2

import com.example.madlabexam.R

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.madlabexam.BaseActivity

class CookieActivity : BaseActivity() {

    private var isHungry = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cookie)

        val ivCharacter = findViewById<ImageView>(R.id.ivCharacter)
        val tvMood = findViewById<TextView>(R.id.tvMood)
        val btnEat = findViewById<Button>(R.id.btnEat)

        btnEat.setOnClickListener {
            if (isHungry) {
                ivCharacter.setImageResource(R.drawable.ic_happy)
                tvMood.text = "I'm so full"
                btnEat.text = "RESET"
            } else {
                ivCharacter.setImageResource(R.drawable.ic_unhappy)
                tvMood.text = "I'm so hungry"
                btnEat.text = "EAT COOKIE"
            }
            isHungry = !isHungry
        }
    }
}
