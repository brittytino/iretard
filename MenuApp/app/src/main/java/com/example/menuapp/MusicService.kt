package com.example.menuapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

class MusicService : Service() {

    private lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (::player.isInitialized && player.isPlaying) {
            return START_STICKY
        }

        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)

        player.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onDestroy() {
        if (::player.isInitialized) {
            player.stop()
            player.release()
        }
        super.onDestroy()
    }

}