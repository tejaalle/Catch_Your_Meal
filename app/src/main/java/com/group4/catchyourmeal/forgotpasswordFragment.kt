package com.group4.catchyourmeal

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class forgotpasswordFragment : Fragment() {

    private val TAG = "ForgotPasswordActivity"
    //UI elements
    private var etEmail: EditText? = null
    private var btnSubmit: Button? = null
    //Firebase references
    private var mAuth: FirebaseAuth? = null

    private var mcontext: Context?=null

    companion object {
        fun newInstance() = forgotpasswordFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_forget_password, container, false)
        mcontext = context
        startForgotPasswordActivity(view)
        return view
    }



    private fun startForgotPasswordActivity(view:View) {
        etEmail = view.findViewById<View>(R.id.et_email) as EditText
        btnSubmit = view.findViewById<View>(R.id.btn_submit) as Button
        mAuth = FirebaseAuth.getInstance()
        btnSubmit!!.setOnClickListener { sendPasswordResetEmail() }
    }
    private fun sendPasswordResetEmail() {
        val email = etEmail?.text.toString()
        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val message = "Email sent."
                        Log.d(TAG, message)
                        Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show()
                        redirecting()
                    } else {
                        Log.w(TAG, task.exception!!.message)
                        Toast.makeText(mcontext, "No user found with the provided email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(mcontext, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }
    private fun redirecting() {
        findNavController().navigate(R.id.navigation_sign_in)
    }

}
