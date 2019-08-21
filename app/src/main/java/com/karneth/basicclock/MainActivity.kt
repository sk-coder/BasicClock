package com.karneth.basicclock

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyCharacterMap
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.preference.PreferenceManager
import com.karneth.basicclock.R
import com.karneth.basicclock.R.layout.activity_main

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    val TAG = "SK BasicClock"
    val timer = Timer("clockTimer", true)

    val formatter12Hour = DateTimeFormatter.ofPattern("hh:mm:ss")
    val formatter24Hour = DateTimeFormatter.ofPattern("HH:mm:ss")

    val listener: SharedPreferences.OnSharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        // Listener event handling can happen here or in a separate function, as is called below by passing the
        // shared preference object and key value to another function
        Log.i(TAG, "Detected change in Preference: " + key)
        onListenerEvent(sharedPreferences, key)
    }

    var is24Hour = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        Log.i(TAG, "OnCreate Called")

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        is24Hour = sharedPreferences.getBoolean("is24Hour", false)

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        fab.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        textBlock.text = getFormattedTime()

        timer.scheduleAtFixedRate(timerTask{ onTimer() }, 0.toLong(), 1000.toLong())
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
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
        var timeString : String

        if (is24Hour) {
            timeString  = current.format(formatter24Hour)
        }
        else {
            timeString  = current.format(formatter12Hour)
        }

        return timeString
    }

    fun onListenerEvent (sharedPreferences: SharedPreferences, key: String) {
        // function to handle values passed in from the listener
//        Log.i(TAG, "This is the Key passed: " + key);
//        Log.i(TAG, "Value of is 24 Hr: " + sharedPreferences.getBoolean(key, false).toString())
        if (key == "is24Hour") {
            Log.i(TAG, "Preference is24Hour updated to: " + sharedPreferences.getBoolean(key, false))
            is24Hour = sharedPreferences.getBoolean(key, false)
        }
    }
}
