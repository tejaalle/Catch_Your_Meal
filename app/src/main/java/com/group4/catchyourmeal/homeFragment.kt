package com.group4.catchyourmeal

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*


class homeFragment : Fragment() {

    companion object {
        fun newInstance() = homeFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageButton1.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_alacarte)
        }
        imageButton2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_combo)
        }
        imageButton3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_meal)
        }
        imageButton4.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_favourites5)
        }
        }




}
