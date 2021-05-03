package org.d3ifcool.tic_ta_toe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.d3ifcool.tic_ta_toe.authentikasi.Login

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT :Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}