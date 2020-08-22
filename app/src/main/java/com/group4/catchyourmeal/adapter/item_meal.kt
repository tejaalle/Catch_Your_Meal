package com.group4.catchyourmeal

import android.content.Intent
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
import com.group4.catchyourmeal.*
import com.group4.catchyourmeal.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_combo.view.*
import kotlinx.android.synthetic.main.item_meal.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class item_meal(private val context: Meal,
                private val items: List<Meallist>): RecyclerView.Adapter<item_meal.ViewHolder>() {
    private  var list: carttable = carttable()
    private var list1: Favouritestable = Favouritestable()
    private lateinit var cartdao: cartDao
    private lateinit var favraritedao: FavouritesDao
    private lateinit var db: mealdatabase
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //attach the custom layout to the recycler view
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_meal, viewGroup, false)
        return ViewHolder(itemView)

    }
    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = items[position]
        //Glide is used to load an image using a url
        Glide.with(context).load(user.Image).into(viewHolder.profileImg)
        viewHolder.nameTxt.text = user.Name
        viewHolder.Price.text=user.price
        viewHolder.Rating.text=user.rating
        viewHolder.mealtype.text=user.type
        var count =1

        viewHolder.itemView.cartbutton2.setOnClickListener {
            count=count+1
            if(count==2) {
                var btn:ImageButton =it.cartbutton2
                val drawable: Drawable = context.resources.getDrawable(R.drawable.addcart_green)
                btn.setImageDrawable(drawable)
                //viewHolder.itemView.cartbutton2.text = "Added to cartDao"
                list.Name = viewHolder.nameTxt.text.toString()
                list.Price = viewHolder.Price.text.toString()
                list.Image = viewHolder.profileImg.toString()

                Log.i("printing entered thread", "entered")
                var job: Job = Job()
                val scope = CoroutineScope(job + Dispatchers.Main)
                db = mealdatabase.getInstance(viewHolder.Price.context)
                cartdao = db.cartDao
                scope.launch {
                    Log.i("printing entered scope", "entered")
                    var id = cartdao.insert(list)
                    Log.i("printing id", "$id")
                }
                }
            else {
                //it.setBackgroundResource(android.R.color.holo_green_dark)
                Toast.makeText(viewHolder.Price.context,"Please go to the cart to remove items",
                    Toast.LENGTH_SHORT).show()
                //viewHolder.itemView.cartbutton2.text = "Add to cartDao"
            }
            // Toast.makeText(this, "added to cartDao",Toast.LENGTH_LONG).show()
        }
        viewHolder.itemView.favourites2.setOnClickListener {
            count=count+1
            if(count==2) {
                //it.setBackgroundResource(android.R.color.holo_red_dark)
                var btn: ImageButton =it.favourites2
                val drawable: Drawable = context.resources.getDrawable(R.drawable.ic_favourite_red)
                btn.setImageDrawable(drawable)
                //viewHolder.itemView.cartbutton.text = "Added to cartDao"
                list1.Name =viewHolder.nameTxt.text.toString()
                list1.Price = viewHolder.Price.text.toString()
                list1.Image = viewHolder.profileImg.toString()

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
        viewHolder.item.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {


                val intent = Intent (context.activity, DetailActivity::class.java)
                intent.putExtra("id", user.id)
                intent.putExtra("description",user.Description)
                intent.putExtra("name",user.Name)
                intent.putExtra("price",user.price)
                intent.putExtra("profilePhotoUrl",user.Image)
                context.startActivity(intent)
            }
        })


//        viewHolder.item.setOnClickListener {
//
//            val activity1 = MainActivity()
//
//            val intent = Intent (activity1, DetailActivity::class.java)
//            context.startActivity(intent)
////            val intent = Intent(context,DetailActivity::class.java)
//            // TODO: pass user field data to Intent.extras
//            // See DetailsActivity for keys. All values should be strings.
//             intent.putExtra("id", user.id)
//            intent.putExtra("description",user.Description)
//            intent.putExtra("name",user.Name)
//            intent.putExtra("price",user.price)
//            intent.putExtra("profilePhotoUrl",user.Image)
//            context.startActivity(intent)
        //      }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImg: ImageView
            get() = itemView.profileImg2
        val nameTxt: TextView
            get() = itemView.nameTxt2
        val Price: TextView
            get() = itemView.price2
        val Rating: TextView
            get()=itemView.Id2
        val item: CardView
            get() = itemView.item_meal
        val mealtype:TextView
            get()= itemView.type

    }

}