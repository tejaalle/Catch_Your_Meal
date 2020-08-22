package com.group4.catchyourmeal

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.group4.catchyourmeal.R
import com.group4.catchyourmeal.cartDao
import com.group4.catchyourmeal.carttable
import com.group4.catchyourmeal.mealdatabase
import com.group4.catchyourmeal.shoppingCartFragment
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_combo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class item_cart(private val context:shoppingCartFragment,private val cartlist:List<carttable>):
    RecyclerView.Adapter<item_cart.ViewHolder>() {
    private lateinit var cartdao: cartDao
    private lateinit var db: mealdatabase
    private var list1: Favouritestable = Favouritestable()
    private lateinit var favraritedao: FavouritesDao
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        //attach the custom layout to the recycler view
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cart, viewGroup, false)
        return ViewHolder(itemView)
    }
    //returns the number of items in the recycler view
    override fun getItemCount() = cartlist.size
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = cartlist[position]
        Log.i("your image is here","my string is${user.Image}")
        Log.i("your name is here","my name is ${user.id}")
        viewHolder.nameTxt.text = user.Name

        viewHolder.Price.text=user.Price
        viewHolder.itemView.cartbutton3.setOnClickListener{

            var job: Job = Job()
            val scope = CoroutineScope(job + Dispatchers.Main)
            db = mealdatabase.getInstance(viewHolder.Price.context)
            cartdao = db.cartDao
            scope.launch {
                Log.i("printing entered scope","entered")
                var id = cartdao.delete(user.id)
                Log.i("printing id","$id")}
            viewHolder.item.removeAllViews()


        }
        var count =1
        viewHolder.itemView.favourites3.setOnClickListener {
            count=count+1
            if(count==2) {
                //it.setBackgroundResource(android.R.color.holo_red_dark)
                var btn: ImageButton =it.favourites3
                val drawable: Drawable = context.resources.getDrawable(R.drawable.ic_favourite_red)
                btn.setImageDrawable(drawable)
                //viewHolder.itemView.cartbutton.text = "Added to cartDao"
                list1.Name =viewHolder.nameTxt.text.toString()
                list1.Price = viewHolder.Price.text.toString()


                Log.i("printing entered thread","entered")
                var job: Job = Job()
                val scope = CoroutineScope(job + Dispatchers.Main)
                db = mealdatabase.getInstance(viewHolder.Price.context)
                favraritedao = db.favouritesDao
                scope.launch {
                    Log.i("printing entered scope","entered")
                    var id = favraritedao.insert(list1)
                    Log.i("printing id","$id")}
            }
            else {
                // it.setBackgroundResource(android.R.color.holo_red_dark)
                Toast.makeText(viewHolder.Price.context,"Please go to the favourites to remove items",
                    Toast.LENGTH_SHORT).show()

            }
            // Toast.makeText(this, "added to cartDao",Toast.LENGTH_LONG).show()
        }

    }
    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        val nameTxt: TextView
            get() = itemView.nameTxt3
        val Price: TextView
            get() = itemView.price3
        val item: CardView
            get() = itemView.item_cart

    }
}