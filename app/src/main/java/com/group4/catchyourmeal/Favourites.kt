package com.group4.catchyourmeal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.group4.catchyourmeal.item_favourites
import com.group4.catchyourmeal.FavouritesDao
import com.group4.catchyourmeal.Favouritestable
import com.group4.catchyourmeal.mealdatabase
import kotlinx.android.synthetic.main.fragment_alacarte.usersRecycler
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class Favourites : Fragment() {
    private  var shoppingCartList:List<Favouritestable> = mutableListOf()
    private lateinit var cartdao: FavouritesDao
    private lateinit var db: mealdatabase



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersRecycler.layoutManager = LinearLayoutManager(context)
        retrivedata(context!!)
    }
    private fun retrivedata(context:Context){

            var job: Job = Job()
            val scope = CoroutineScope(job + Dispatchers.Main)
            db = mealdatabase.getInstance(context)
            cartdao = db.favouritesDao
            scope.launch { shoppingCartList = cartdao.getAll()

        if(shoppingCartList.isEmpty()){
            textView.visibility = View.VISIBLE
        }
        else {
            textView.visibility = View.GONE
            usersRecycler.adapter = item_favourites(this@Favourites, shoppingCartList)

        }
            }



    }


}
