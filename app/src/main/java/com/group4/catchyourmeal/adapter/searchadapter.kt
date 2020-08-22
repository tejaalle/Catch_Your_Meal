package com.group4.catchyourmeal

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.group4.catchyourmeal.*
//import com.group4.catchyourmeal.database.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item.view.Id
import kotlinx.android.synthetic.main.item.view.cartbutton
import kotlinx.android.synthetic.main.item.view.favourites
import kotlinx.android.synthetic.main.item.view.nameTxt
import kotlinx.android.synthetic.main.item.view.price
import kotlinx.android.synthetic.main.item.view.profileImg
import kotlinx.android.synthetic.main.searchitem.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class searchadapter(private val context: searchFragment,
private val items: List<itemlist>
) : RecyclerView.Adapter<searchadapter.ViewHolder>(), Filterable {
    private var list: carttable = carttable()
    private var list1: Favouritestable = Favouritestable()
    private lateinit var cartdao: cartDao
    private lateinit var favraritedao: FavouritesDao
    private lateinit var db: mealdatabase

    internal var filterlist: List<itemlist> = items
    var mainact = searchFragment()
    var listis = mainact.itemlst
    var TAG = "Adapter"

    override fun getFilter(): Filter {
        Log.i(TAG, "Filter called")
        return object : Filter() {
            //run on background thread
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                Log.i(TAG, "$constraint")
                if (constraint.toString().isEmpty()) {
                    filterlist = items
                    Log.i("Pavansai", "Hope it not works $filterlist")
                } else {
                    var resultlist = mutableListOf<itemlist>()
                    for (item in items) {
                        Log.i(TAG, "Printing items ${item}")
                        Log.i(TAG, "Printing item name ${item.Name}")
                        Log.i(TAG, "printing constraint $constraint")
                        if (item.Name.toLowerCase(Locale.ROOT).contains(
                                constraint.toString().toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultlist.add(item)
                            Log.i(TAG, "printing resultlist ${resultlist}")
                        }
                    }
                    Log.i(TAG, "Result is $resultlist")
                    filterlist = resultlist
                }
                val filterresults: FilterResults = Filter.FilterResults()
                filterresults.values = filterlist
                return filterresults
            }

            //Runs on UI Thread
            override fun publishResults(constraint: CharSequence?, filterresults: FilterResults?) {
                Log.i(TAG, "Filterresults are$filterresults")
                filterlist.toMutableList().addAll(filterresults?.values as Collection<itemlist>)
                //list.clear()
                listis = filterlist as MutableList<itemlist>
                Log.i(TAG, "The final step${list}")
                mainact.adapterr = searchadapter(context, listis)
                notifyDataSetChanged() to mainact.adapterr

            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //attach the custom layout to the recycler view
        val itemView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.searchitem, viewGroup, false)
        return ViewHolder(itemView)

    }

    //returns the number of items in the recycler view
    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        var db = Room.databaseBuilder(context.activity!!.applicationContext, mealdatabase::class.java,"meal_database").build()
        val user = items[position]
        //Glide is used to load an image using a url
        Glide.with(context).load(user.Image).into(viewHolder.profileImg)
        viewHolder.nameTxt.text = user.Name
        viewHolder.Price.text = user.price
        viewHolder.Rating.text = user.rating
        var count = 1

        viewHolder.itemView.cart_button.setOnClickListener {
            count = count + 1
            if (count == 2) {
                var btn:ImageButton =it.cart_button
                val drawable: Drawable = context.resources.getDrawable(R.drawable.addcart_green)
                btn.setImageDrawable(drawable)
                //viewHolder.itemView.cartbutton.text = "Added to cartDao"
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


//                thread {
//                    db.cartDao().insert(list)
//                }.start()


            } else {
                //it.setBackgroundResource(android.R.color.holo_green_dark)
                Toast.makeText(
                    viewHolder.Price.context, "Please go to the cart to remove items",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewHolder.itemView.favourites.setOnClickListener {
            count = count + 1
            if (count == 2) {
                var btn:ImageButton =it.favourites
                val drawable: Drawable = context.resources.getDrawable(R.drawable.ic_favourite_red)
                btn.setImageDrawable(drawable)
                //viewHolder.itemView.cartbutton.text = "Added to cartDao"
                list1.Name = viewHolder.nameTxt.text.toString()
                list1.Price = viewHolder.Price.text.toString()
                list1.Image = viewHolder.profileImg.toString()

                Log.i("printing entered thread", "entered")
                var job: Job = Job()
                val scope = CoroutineScope(job + Dispatchers.Main)
                db = mealdatabase.getInstance(viewHolder.Price.context)
                favraritedao = db.favouritesDao
                scope.launch {
                    Log.i("printing entered scope", "entered")
                    var id = favraritedao.insert(list1)
                    Log.i("printing id", "$id")
                }
            } else {
                //it.setBackgroundResource(android.R.color.holo_red_dark)
                Toast.makeText(
                    viewHolder.Price.context, "Please go to the favourites to remove items",
                    Toast.LENGTH_SHORT
                ).show()

            }
            // Toast.makeText(this, "added to cartDao",Toast.LENGTH_LONG).show()
        }
        viewHolder.item.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                val intent = Intent(context.activity, DetailActivity::class.java)
                intent.putExtra("id", user.id)
                intent.putExtra("description", user.Description)
                intent.putExtra("name", user.Name)
                intent.putExtra("price", user.price)
                intent.putExtra("profilePhotoUrl", user.Image)
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
            get() = itemView.profileImg
        val nameTxt: TextView
            get() = itemView.nameTxt
        val Price: TextView
            get() = itemView.price
        val Rating: TextView
            get() = itemView.Id
        val item: CardView
            get() = itemView.searchitem

    }
}
