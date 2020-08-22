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
import kotlinx.android.synthetic.main.item.view.*
//import com.group4.catchyourmeal.Favourites
//import com.group4.catchyourmeal.R
//import com.group4.catchyourmeal.database.*
import kotlinx.android.synthetic.main.item_favourites.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class item_favourites(private val context:Favourites,private val favouritelist:List<Favouritestable>):
    RecyclerView.Adapter<item_favourites.ViewHolder>() {
    private lateinit var cartdao: FavouritesDao
    private lateinit var cartdao1:cartDao
    private lateinit var db: mealdatabase
    private  var list: carttable = carttable()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        //attach the custom layout to the recycler view
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_favourites, viewGroup, false)
        return ViewHolder(itemView)
    }
    override fun getItemCount() = favouritelist.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = favouritelist[position]

        //Glide.with(context).load(user.Image).into(viewHolder.profileImg)
        viewHolder.nameTxt.text = user.Name
        viewHolder.Price.text=user.Price
        var count =1
        viewHolder.itemView.cartbutton4.setOnClickListener {
            count=count+1
            if(count==2) {
                var btn: ImageButton =it.cartbutton4
                val drawable: Drawable = context.resources.getDrawable(R.drawable.addcart_green)
                btn.setImageDrawable(drawable)
                //viewHolder.itemView.cartbutton2.text = "Added to cartDao"
                list.Name = viewHolder.nameTxt.text.toString()
                list.Price = viewHolder.Price.text.toString()
                //list.Image = viewHolder.profileImg.toString()

                Log.i("printing entered thread", "entered")
                var job: Job = Job()
                val scope = CoroutineScope(job + Dispatchers.Main)
                db = mealdatabase.getInstance(viewHolder.Price.context)
                cartdao1 = db.cartDao
                scope.launch {
                    Log.i("printing entered scope", "entered")
                    var id = cartdao1.insert(list)
                    Log.i("printing id", "$id")
                }
            }
            else {
                //it.setBackgroundResource(android.R.color.holo_green_dark)
                Toast.makeText(viewHolder.Price.context,"Please go to the cart to remove items",Toast.LENGTH_SHORT).show()

                //viewHolder.itemView.cartbutton2.text = "Add to cartDao"
            }
        }
        viewHolder.itemView.favourites4.setOnClickListener{
            var job: Job = Job()
            val scope = CoroutineScope(job + Dispatchers.Main)
            db = mealdatabase.getInstance(viewHolder.Price.context)
            cartdao = db.favouritesDao
            scope.launch {
                Log.i("printing entered scope","entered")
                var id = cartdao.delete(user.id)
                Log.i("printing id","$id")}
            viewHolder.item.removeAllViews()
        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val profileImg: ImageView
//            get() = itemView.profileImg4
        val nameTxt: TextView
            get() = itemView.nameTxt4
        val Price: TextView
            get() = itemView.price4
        val item: CardView
            get() = itemView.item_favorites

    }

}