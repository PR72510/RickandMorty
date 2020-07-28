package com.example.rickandmorty.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.rickandmorty.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
//            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
//
//            }
//
//            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
//
//            }
//
//            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
//
//            }
//
//            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
//                startActivity(Intent(this@SplashActivity , MainActivity::class.java))
//            }
//
//        })

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 1500)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus)
            hideSystemAndUINavigation(this)
    }

    private fun hideSystemAndUINavigation(activity: Activity) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}