package com.group4.catchyourmeal

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.let(this::populateFromIntent)
        // new navigation style example:
    }

    private fun populateFromIntent(intent: Intent) {
        nameTxt.text = intent.getStringExtra(NAME)
        Price.text = intent.getStringExtra(PRICE)
        Id.text = intent.getStringExtra(DESCRIPTION)
        Glide.with(this).load(intent.getStringExtra(PROFILE_PHOTO_URL_KEY)).into(imageView)
    }

}
