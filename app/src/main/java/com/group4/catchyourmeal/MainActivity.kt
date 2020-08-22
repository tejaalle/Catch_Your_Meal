package com.group4.catchyourmeal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val spashTime:Long=5000 // 3 sec
    lateinit var bgapp: ImageView

    lateinit var texthome : LinearLayout
    lateinit var logoframe : LinearLayout
    lateinit var menus: LinearLayout

    lateinit var frombottom: Animation
    lateinit var fadein: Animation
    lateinit var fadeout: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom)
        fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        var clover = AnimationUtils.loadAnimation(this, R.anim.clovernim) as Animation
        bgapp = findViewById<ImageView>(R.id.bgapp)



        logoframe = findViewById<LinearLayout>(R.id.logo_layout)

        logoframe.startAnimation(frombottom)
        //logoframe.startAnimation(clover)

        Thread.sleep(1000);




        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            //bgapp.animate().translationY(-2500f).setDuration(1600).startDelay = 800
            logoframe.startAnimation(fadeout)


            startActivity(Intent(this,HomeActivity::class.java))

            // close this activity
            finish()
        }, spashTime)
    }
}