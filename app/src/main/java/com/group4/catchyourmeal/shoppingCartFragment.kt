package com.group4.catchyourmeal

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
//import com.group4.catchyourmeal.adapter.item_cart
import com.group4.catchyourmeal.cartDao
//import com.group4.catchyourmeal.database.carttable
///import com.group4.catchyourmeal.database.mealdatabase
import kotlinx.android.synthetic.main.fragment_alacarte.*
import kotlinx.android.synthetic.main.fragment_alacarte.usersRecycler
import kotlinx.android.synthetic.main.fragment_shoppingcart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class shoppingCartFragment : Fragment() {

    private  var shoppingCartList:List<carttable> = mutableListOf()
    private lateinit var cartdao: cartDao
    private lateinit var db: mealdatabase


   // private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //viewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel::class.java)
        return inflater.inflate(R.layout.fragment_shoppingcart, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersRecycler.layoutManager = LinearLayoutManager(context)
//        var itemlist:MutableList<carttable> = mutableListOf<carttable>()
//        itemlist.add(carttable(id = 0, Name = "chicken", Price = "789"))
//        itemlist.add(carttable(id = 1, Name = "mutton", Price = "345"))
//        usersRecycler.adapter = item_cart(this,itemlist)
       retrivedata(context!!)
        checkout.setOnClickListener {
            var job: Job = Job()
            val scope = CoroutineScope(job + Dispatchers.Main)
            db = mealdatabase.getInstance(context!!)
            cartdao = db.cartDao
            scope.launch {

                 cartdao.deleteAll()


            Navigation.findNavController(view).navigate(R.id.action_navigation_shopping_cart_to_placeorder)}

        }
        if(item_cart(this@shoppingCartFragment, shoppingCartList).itemCount==0){
            checkout.visibility = View.GONE
        }


    }
    private fun retrivedata(context:Context){

            var job: Job = Job()
            val scope = CoroutineScope(job + Dispatchers.Main)
            db = mealdatabase.getInstance(context)
            cartdao = db.cartDao
            scope.launch {
                Log.i("printing id 1","${cartdao.get(1)}")
                shoppingCartList = cartdao.getAll()
            Log.i("printing your shoppingcart","$shoppingCartList")

        if(shoppingCartList.isEmpty()){
            Log.i("printing reason","shopping cart list")
            textView4.visibility = View.VISIBLE
            checkout.visibility=View.GONE
        }
        else {
            Log.i("printing reason   reason  reason ","shopping cart list")
            textView4.visibility = View.GONE
            checkout.visibility= View.VISIBLE
            usersRecycler.adapter = item_cart(this@shoppingCartFragment, shoppingCartList)}

        }


    }

}
