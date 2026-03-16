package com.example.madlabexam.mock1

import com.example.madlabexam.R

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.madlabexam.BaseActivity
import com.google.android.material.appbar.MaterialToolbar

class MainMenuActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_home)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_whatsapp -> {
                startActivity(Intent(this, WhatsAppMessageActivity::class.java))
                true
            }

            R.id.menu_play_music -> {
                startService(Intent(this, MusicService::class.java))
                Toast.makeText(this, "♪ Music Started", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.menu_theme_toggle -> {
                Toast.makeText(this, "Theme toggle demo", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
