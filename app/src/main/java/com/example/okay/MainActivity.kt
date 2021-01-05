package com.example.okay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))
        Handler().postDelayed({


                logoImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
                Handler().postDelayed({
                    logoImage.visibility = View.GONE

                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                    finish()

                }, 1500)// delay in 1s
        }, 3000)// delay in 1.5s



    }
}