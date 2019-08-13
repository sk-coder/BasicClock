package com.karneth.basicclock

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
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
