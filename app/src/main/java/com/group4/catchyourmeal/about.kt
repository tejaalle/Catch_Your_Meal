package com.group4.catchyourmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class about : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val actionbar = supportActionBar
        actionbar!!.title = "Back to Profile"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        var yourString: String =getString(R.string.About)
        var obj=Element()



        val aboutPage = AboutPage(this)
            .isRTL(false)
            .setImage(R.drawable.logo)
            . setDescription(yourString)
            .addItem(obj.setTitle("Help us improve"))
            .addGroup("Connect with us")
            .addEmail("catchyourmeal@gmail.com")
            .addWebsite("https://www.catchyourmeal.com/")
            .addFacebook("catch_your_meal")
            .addPlayStore("catchYourMeal")
            .create()


           setContentView(aboutPage)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
