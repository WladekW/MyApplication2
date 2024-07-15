package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adddatabase.DdHelper

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {

    private lateinit var clickSound: MediaPlayer
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "MyPrefsFile"
        const val SOUND_SWITCH_STATE = "sound_switch_state"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val heightABC = Resources.getSystem().displayMetrics.heightPixels

        clickSound = MediaPlayer.create(this, R.raw.click_music)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val switchState = sharedPreferences.getBoolean(SOUND_SWITCH_STATE, false)

        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val win1: TextView = findViewById(R.id.textView1)
        val win2: TextView = findViewById(R.id.textView2)
        val back: ImageButton = findViewById(R.id.backButton)

        val db = DdHelper(this, null)
        val player = db.getPlayer()

        if (player != null) {
            button1.text = player.name1
            button2.text = player.name2
        }

        button1.isEnabled = true
        button2.isEnabled = true

        button1.layoutParams.height = dpToPx((heightABC * 0.285).toInt())
        button2.layoutParams.height = dpToPx((heightABC * 0.285).toInt())

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        button1.setOnClickListener {
            val newLayoutParams1 = button1.layoutParams.apply { height += 20 }
            button1.layoutParams = newLayoutParams1

            val newLayoutParams2 = button2.layoutParams.apply { height -= 20 }
            button2.layoutParams = newLayoutParams2

            if (switchState) {
                playClickSound()
            }

            if (button2.layoutParams.height <= dpToPx((heightABC * 0.145).toInt())) {
                var countWin2 = win2.text.toString().toInt()
                countWin2 += 1
                win2.text = countWin2.toString().trim()

                button1.isEnabled = false
                button2.isEnabled = false

                object : CountDownTimer(3000, 1000) {
                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        button1.text = "Timeout: ${millisUntilFinished / 1000}s"
                        button2.text = "Timeout: ${millisUntilFinished / 1000}s"
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onFinish() {
                        button1.text = player?.name1
                        button2.text = player?.name2
                    }
                }.start()

                Handler().postDelayed({
                    button1.layoutParams.height = dpToPx((heightABC * 0.285).toInt())
                    button2.layoutParams.height = dpToPx((heightABC * 0.285).toInt())
                    button1.requestLayout()
                    button2.requestLayout()

                    button1.isEnabled = true
                    button2.isEnabled = true
                }, 3000)
            }
        }

        button2.setOnClickListener {
            val newLayoutParams2 = button2.layoutParams.apply { height += 20 }
            button2.layoutParams = newLayoutParams2

            val newLayoutParams1 = button1.layoutParams.apply { height -= 20 }
            button1.layoutParams = newLayoutParams1

            if (switchState) {
                playClickSound()
            }

            if (button1.layoutParams.height <= dpToPx((heightABC * 0.145).toInt())) {
                var countWin1 = win1.text.toString().toInt()
                countWin1 += 1
                win1.text = countWin1.toString().trim()

                button1.isEnabled = false
                button2.isEnabled = false

                object : CountDownTimer(3000, 1000) {
                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        button1.text = "Timeout: ${millisUntilFinished / 1000}s"
                        button2.text = "Timeout: ${millisUntilFinished / 1000}s"
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onFinish() {
                        button1.text = player?.name1
                        button2.text = player?.name2
                    }
                }.start()

                Handler().postDelayed({
                    button1.layoutParams.height = dpToPx((heightABC * 0.285).toInt())
                    button2.layoutParams.height = dpToPx((heightABC * 0.285).toInt())
                    button1.requestLayout()
                    button2.requestLayout()

                    button1.isEnabled = true
                    button2.isEnabled = true
                }, 3000)
            }
        }
    }

    private fun playClickSound() {
        clickSound.seekTo(0)
        clickSound.start()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
