package com.example.surfhomework1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SurfBroadcastReceiver :  BroadcastReceiver() {
    companion object {
        var message: String? = null
    }
    override fun onReceive(context: Context?, intent: Intent?){
        if (intent?.action == "ru.shalkoff.vsu_lesson2_2024.SURF_ACTION") {
            val text = intent.getStringExtra("message")
            message = text
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}