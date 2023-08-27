package com.muratalarcin.yemektarifi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RelativeLayout
import com.muratalarcin.yemektarifi.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Animasyonu başlat
        val animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in)
        findViewById<RelativeLayout>(R.id.splash_screen_activity).startAnimation(animation)
        // Splash ekranını göstermek ve ana aktiviteye geçmek için Handler kullanımı
        Handler(Looper.getMainLooper()).postDelayed({
            startMainActivity()

        }, SplashScreenActivity.SPLASH_DURATION.toLong())
    }

    companion object {
        private const val SPLASH_DURATION = 2000 // saniye
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Splash ekranını kapatmak için
    }

}