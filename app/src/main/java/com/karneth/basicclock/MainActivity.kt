package com.karneth.basicclock

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.karneth.basicclock.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    val TAG = "SK BasicClock"
    private val timer = Timer("clockTimer", true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        textBlock.text = getFormattedTime()

        timer.scheduleAtFixedRate(timerTask{ onTimer() }, 0.toLong(), 1000.toLong())
    }

    fun onTimer() {
        val currentTime = getFormattedTime()
        Log.i(TAG, currentTime)
        this@MainActivity.runOnUiThread(Runnable {
            this.textBlock.text = currentTime
        })
    }

    fun getFormattedTime () : String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss")
        var timeString : String = current.format(formatter)
        return timeString
    }
}
