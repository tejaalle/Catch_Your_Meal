package com.group4.catchyourmeal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_alacarte.*
import org.jetbrains.annotations.NotNull
import com.group4.catchyourmeal.item
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.group4.catchyourmeal.R


private const val TAG = "MyActivity"

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [alacarte.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [alacarte.newInstance] factory method to
 * create an instance of this fragment.
 */
class alacarte : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_alacarte, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("teja","entered")
      //  Toast.makeText(context, "i am in alarcate",Toast.LENGTH_SHORT).show()
        usersRecycler.layoutManager = LinearLayoutManager(context)
        retrievedata()

    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */



    private fun retrievedata() {
       // Toast.makeText(context, "i am in alarcate",Toast.LENGTH_SHORT).show()
        var itemlst:MutableList<itemlist> = mutableListOf<itemlist>()
        val db :FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Testing")
            .get()
            .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot> {
                override fun onComplete(@NonNull task: Task<QuerySnapshot>) {
                    if (task.isSuccessful())
                    {
                        for (document :QueryDocumentSnapshot in task.getResult()!!)
                        {
                            var count=0
                            Log.i(TAG, "output")
                            val data=document.getData()
                            val id=data.getValue("id")
                            val Description = data.getValue("Description")
                            val image=data.getValue("Image")
                            val name=data.getValue("Name")
                            val rating=data.getValue("Rating")
                            val price=data.getValue("Price")
                            Log.i("myactivity", "object 3" +data.toString())
                            itemlst.add(count, itemlist(id.toString(),Description.toString(),image.toString(),name.toString(),
                                rating.toString(),price.toString()))

                            // Log.d(TAG, itemlst.get(0).toString())

                            Log.d(TAG, document.getId() + "see data => " + itemlst)
                        }
                        usersRecycler.adapter = item(this@alacarte, itemlst)
                    }
                    else
                    {
                        Log.i("myactivity", "Error getting documents.", task.getException())
                    }
                }
            })
    }
}
