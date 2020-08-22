package com.group4.catchyourmeal


import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element


class aboutusFragment : Fragment() {

    var mcontext :Context ?= null
    private var backBtn: ImageView? = null
    private var TAG = "AboutusFragment"
    var layout: RelativeLayout?=null
        //var activity :Activity ?= null
    companion object {
        fun newInstance() = aboutusFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mcontext = context

        var view:View = inflater.inflate(R.layout.fragment_aboutus, container, false)
        backBtn = view.findViewById(R.id.back_arrow)
        layout = view.findViewById(R.id.relativecontent)
        return view
    }






    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        var yourString: String =getString(R.string.About)
        var obj=Element()



        val aboutPage = AboutPage(mcontext)
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


        activity?.setContentView(aboutPage)

        backBtn!!.setOnClickListener {
            Log.d(TAG, "click on back password")
            findNavController().navigate(R.id.navigation_profile)}
    }

}
