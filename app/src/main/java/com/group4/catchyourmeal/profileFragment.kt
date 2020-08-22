package com.group4.catchyourmeal

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_signup.*


class profileFragment : Fragment() {

    private var firebaseDatabaseReference: DatabaseReference? = null
    private var fireabseDatabase: FirebaseDatabase? = null


    //UI elements
    private var tvFirstName: TextView? = null
    private var tvLastName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvEmailVerifiied: TextView? = null
    private var tveditProfile: TextView? = null
    private var tvaboutus: TextView? = null
    private var tvsupport: TextView? = null
    private var favourite: TextView? = null
    val RC_SIGN_IN: Int = 1
    private val TAG = "SignInActivity"
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String? = null
    private var password: String? = null
    private var name: String? = null
    private var photoUr: Uri? = null
    private var isEmailVerified: Boolean = false
    private var profileimage : ImageView?=null

    private var etmessage: TextView?=null


    companion object {
        fun newInstance() = profileFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeFirebase()
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        startProfileFragment(view)

        val mUser = firebaseAuth.currentUser
        if(mUser!=null) {
            mUser?.let {
                // Name, email address, and profile photo Url
                name = mUser.displayName
                /*if(name=="")
                {
                    val firstname =
                }*/
                email = mUser.email
                if(mUser.photoUrl!=null){
                photoUr = mUser.photoUrl}

                // Check if user's email is verified
                isEmailVerified = mUser.isEmailVerified

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                val uid = mUser.uid
            }


            val mUserReference = firebaseDatabaseReference!!.child(mUser!!.uid)
            tvEmail!!.text = email
            tvFirstName!!.text = name
            tvLastName!!.text = ""
            if(photoUr!=null){
                profileimage?.let { Glide.with(this).load(photoUr).into(it) };

            }
            if(isEmailVerified){
            tvEmailVerifiied!!.text = "verified"
                var signOutButton = view.findViewById<View>(R.id.logout)
                signOutButton.setOnClickListener{
                    FirebaseAuth.getInstance().signOut()

                    findNavController().navigate(R.id.navigation_profile)
                }}
            else {
                tvEmailVerifiied!!.text = "unverified"
                var signInButton = view.findViewById<View>(R.id.logout) as TextView
                signInButton.text = "Sign In/Sign Up"
                etmessage!!.text="Please verify. Activation link sent"
                signInButton.setOnClickListener{

                    findNavController().navigate(R.id.navigation_sign_in)
                }
            }

            /*mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    tvFirstName!!.text = snapshot.child("firstName").value as String
                    tvLastName!!.text = snapshot.child("lastName").value as String
                    tvEmail!!.text = snapshot.child("email").value as String
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })*/



        }

        else{
            tvEmail!!.visibility=View.INVISIBLE
            tvEmailVerifiied!!.visibility=View.INVISIBLE
            //tveditProfile!!.visibility=View.INVISIBLE

            var signInButton = view.findViewById<View>(R.id.logout) as TextView
            signInButton.text = "Sign In/Sign Up"
            signInButton.setOnClickListener{

                findNavController().navigate(R.id.navigation_sign_in)
            }


        }

        tvaboutus?.setOnClickListener{

            var intent = Intent(context,about::class.java)
            context?.startActivity(intent)
        }
        tvsupport?.setOnClickListener{

            findNavController().navigate(R.id.navigation_fragment_faqsact)
        }
        favourite?.setOnClickListener{

            findNavController().navigate(R.id.favourites5)
        }

        return view
    }




    private fun startProfileFragment(view:View) {

        tvFirstName = view.findViewById<View>(R.id.tvFirstName) as TextView
        tvLastName = view.findViewById<View>(R.id.tvLastName) as TextView
        tvEmail = view.findViewById<View>(R.id.tvEmail) as TextView
        tvEmailVerifiied = view.findViewById<View>(R.id.tvEmailVerifiied) as TextView
        //tveditProfile = view.findViewById<View>(R.id.edit_personal_tv) as TextView
        tvaboutus = view.findViewById<View>(R.id.about_us) as TextView
        tvsupport = view.findViewById<View>(R.id.supporthelp) as TextView
        favourite = view.findViewById<View>(R.id.favourite) as TextView
        profileimage = view.findViewById<View>(R.id.profileCircleImageView) as ImageView
        etmessage = view.findViewById<View>(R.id.tv_message) as TextView
        /* btnSignout=findViewById<View>(R.id.sign_out_button) as Button
        btnSignout!!.setOnClickListener { signOut() }*/
    }

    private fun initializeFirebase() {
        fireabseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabaseReference = fireabseDatabase!!.reference!!.child("Users")
        firebaseAuth = FirebaseAuth.getInstance()

    }






}
