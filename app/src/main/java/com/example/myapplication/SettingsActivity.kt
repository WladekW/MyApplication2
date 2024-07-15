package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adddatabase.DdHelper

@Suppress("DEPRECATION")
class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bgSoundSwitch: Switch

    companion object {
        const val PREFS_NAME = "MyPrefsFile"
        const val SOUND_SWITCH_STATE = "sound_switch_state"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val back: ImageButton = findViewById(R.id.backButton)
        val name1: EditText = findViewById(R.id.name_palyer1)
        val name2: EditText = findViewById(R.id.name_palyer2)
        val switchSound: Switch = findViewById(R.id.click_sound)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        switchSound.isChecked = sharedPreferences.getBoolean(SOUND_SWITCH_STATE, false)

        switchSound.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(SOUND_SWITCH_STATE, isChecked).apply()
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)

            val player1name = name1.text.toString().trim()
            val player2name = name2.text.toString().trim()

            val names = Players(player1name, player2name)
            val db = DdHelper(this, null)
            db.addNames(names)
            Toast.makeText(this, "Changes were saved", Toast.LENGTH_SHORT).show()
        }
    }
}
