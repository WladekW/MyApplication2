package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val play: Button = findViewById(R.id.button_play)
        val setButton: ImageButton = findViewById(R.id.set_button)

        play.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            this.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_left);
        }
        setButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            this.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom );
        }
    }
}