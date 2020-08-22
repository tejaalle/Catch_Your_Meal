package com.group4.catchyourmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
const val PRICE = "price"
const val DESCRIPTION = "description"
const val NAME = "name"
const val PROFILE_PHOTO_URL_KEY = "profilePhotoUrl"


class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val actionbar = supportActionBar
        actionbar!!.title = "Item Details"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState != null) {
            val fragment = DetailFragment()
            fragment.arguments = intent.extras
            supportFragmentManager.beginTransaction()
                .add(R.id.my_nav_host_fragment1,fragment)
                .commit()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
