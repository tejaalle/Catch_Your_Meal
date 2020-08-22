package com.group4.catchyourmeal

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class signupfragment : Fragment() {

    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private val TAG = "CreateAccountActivity"
    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    private var progressDialog: ProgressDialog? = null

    private var mContext: Context? = null
    private var backBtn: ImageView? = null

    companion object {
        fun newInstance() = signupfragment()
    }

    private lateinit var viewModel: SearchViewModel
    private var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_signup, container, false)
        mContext = context
        startCreateAccountActivity(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context!!)
        if (context is Activity) {
            mActivity = context
        }
    }


    private fun startCreateAccountActivity(view:View) {
        etFirstName = view.findViewById<View>(R.id.et_first_name) as EditText
        etLastName = view.findViewById<View>(R.id.et_last_name) as EditText
        etEmail = view.findViewById<View>(R.id.et_email) as EditText
        etPassword = view.findViewById<View>(R.id.et_password) as EditText
        etConfirmPassword = view.findViewById<View>(R.id.et_confirm_password) as EditText
        btnCreateAccount = view.findViewById<View>(R.id.button_register) as Button
        progressDialog = ProgressDialog(mContext)
        backBtn = view.findViewById(R.id.back_arrow)

        etFirstName!!.addTextChangedListener(MyTextWatcher(etFirstName!!, mActivity!!))
        etLastName!!.addTextChangedListener(MyTextWatcher(etLastName!!, mActivity!!))
        etEmail!!.addTextChangedListener(MyTextWatcher(etEmail!!, mActivity!!))
        etPassword!!.addTextChangedListener(MyTextWatcher(etPassword!!, mActivity!!))

        mProgressBar = ProgressDialog(activity)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
        backBtn!!.setOnClickListener {
            Log.d(TAG, "click on back password")
            findNavController().navigate(R.id.navigation_sign_in)}
    }

    private fun createNewAccount() {
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {
            showProgressDialogWithTitle(
                "Success",
                "Registration Successful. PLease verify your email before login"
            )
            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(mActivity!!) { task ->
                    hideProgressDialogWithTitle()
            /*activity?.parent?.let {
                mAuth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(it) { task ->
                        hideProgressDialogWithTitle()*/
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            doFakeWork()
                            val userId = mAuth!!.currentUser!!.uid
                            //Verify Email
                            verifyEmail();
                            //update user profile information
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("firstName").setValue(firstName)
                            currentUserDb.child("lastName").setValue(lastName)
                            updateUserInfoAndUI()
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                activity, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
         else {
            Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show()

        }
    }

    private fun redirectToSignInActivity() {

        findNavController().navigate(R.id.navigation_profile)
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        findNavController().navigate(R.id.navigation_profile)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(mActivity!!) { task ->
                if (task.isSuccessful) {
                    showProgressDialogWithTitle(
                        "Verification email sent",
                        "Verification Email Sent. Please verify before login"
                    )
                    doFakeWork()
                    Toast.makeText(
                        mContext,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressDialogWithTitle()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        mContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun showProgressDialogWithTitle(title: String, substring: String) {
        progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog?.setCancelable(false);
        //Setting Title
        progressDialog?.setTitle(title);
        progressDialog?.setMessage(substring);
        progressDialog?.show();

    }

    // Method to hide/ dismiss Progress bar
    private fun hideProgressDialogWithTitle() {
        progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog?.dismiss();
    }

    private fun doFakeWork() {
        try {
            Thread.sleep(1000);
        } catch (e: InterruptedException) {
            e.printStackTrace();
        }
    }

}
