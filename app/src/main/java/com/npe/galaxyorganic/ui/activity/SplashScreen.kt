package com.npe.galaxyorganic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.npe.galaxyorganic.R
import android.content.Intent
import android.os.Handler


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 2000L) //3000 L = 3 detik
    }
}
