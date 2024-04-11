package com.example.surfhomework1

import android.content.IntentFilter
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val surfBroadcastReceiver = SurfBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val secretKeyButton = findViewById<Button>(R.id.secret_key_button)
        secretKeyButton.setOnClickListener {
            Toast.makeText(this, getSecretKey(), Toast.LENGTH_SHORT).show()
        }

        val surfBroadcastFilter = IntentFilter("ru.shalkoff.vsu_lesson2_2024.SURF_ACTION")
        registerReceiver(surfBroadcastReceiver, surfBroadcastFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(surfBroadcastReceiver)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(
                "secret key",
                getSecretKey()
            )
            putString(
                "message",
                SurfBroadcastReceiver.message
            )
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MyLog", savedInstanceState.getString("secret key").toString())
        Log.d("MyLog", savedInstanceState.getString("message").toString())
    }

    @Suppress("Range")
    private fun getSecretKey() : String{
        val resolver = contentResolver
        val uri = Uri.parse("content://dev.surf.android.provider/text")

        val cursor = resolver.query(uri,null,null,null,null)

        var text = ""

        cursor?.use {
            if (it.moveToFirst()) {
                text = it.getString(it.getColumnIndex("text"))
            }
        }
        return text
    }
}