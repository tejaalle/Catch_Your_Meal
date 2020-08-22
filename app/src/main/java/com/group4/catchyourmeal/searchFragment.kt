package com.group4.catchyourmeal

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.group4.catchyourmeal.item
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

import com.group4.catchyourmeal.searchadapter
import kotlinx.android.synthetic.main.fragment_search.*


class searchFragment : Fragment() {

  lateinit var adapterr:searchadapter
    var itemlst: MutableList<itemlist> = mutableListOf<itemlist>()

    companion object {
        fun newInstance() = searchFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)

    }
    private lateinit var viewModel: SearchViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.updateActionBarTitle("Custom Title From Fragment")
        usersRecycler.layoutManager = LinearLayoutManager(context)
        retrievedata()
        setHasOptionsMenu(true)
    }

private fun retrievedata() {
    val TAG = "searchfragment"
    // Toast.makeText(context, "i am in alarcate",Toast.LENGTH_SHORT).show()

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    db.collection("Testing")
        .get()
        .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(@NonNull task: Task<QuerySnapshot>) {
                if (task.isSuccessful()) {
                    for (document: QueryDocumentSnapshot in task.getResult()!!) {
                        var count = 0
                        Log.i(TAG, "output")
                        val data = document.getData()
                        val id = data.getValue("id")
                        val Description = data.getValue("Description")
                        val image = data.getValue("Image")
                        val name = data.getValue("Name")
                        val rating = data.getValue("Rating")
                        val price = data.getValue("Price")
                        Log.i("myactivity", "object 3" + data.toString())
                        itemlst.add(
                            count, itemlist(
                                id.toString(),
                                Description.toString(),
                                image.toString(),
                                name.toString(),
                                rating.toString(),
                                price.toString()
                            )
                        )

                        // Log.d(TAG, itemlst.get(0).toString())

                        Log.d(TAG, document.getId() + "see data => " + itemlst)
                    }
                    adapterr = searchadapter(this@searchFragment, itemlst)
                    usersRecycler.adapter=adapterr
                } else {
                    Log.i("myactivity", "Error getting documents.", task.getException())
                }
            }
        })
}
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu to use in the action bar

        inflater.inflate(R.menu.searchview, menu)
        var item = menu!!.findItem(R.id.actionsearch)
        var searchview = item!!.getActionView() as SearchView
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // adapter?.filter?.filter(query)
                Log.i("printing query text","$query")
                //renderlist
                return false //To change body of created functions use File | Settings | File Templates.

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("printing new text","$newText")
                adapterr.filter.filter(newText)

                return true  //To change body of created functions use File | Settings | File Templates.
            }
        })
        return super.onCreateOptionsMenu(menu,inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.actionsearch) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


