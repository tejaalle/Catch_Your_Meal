package com.group4.catchyourmeal

import android.app.Activity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText

class MyTextWatcher(view: View, activity: Activity) : TextWatcher {
    private var views: View = view
    private var email: String? = null
    private var password: String? = null
    private var etEmail: EditText?=null
    private var etPassword: EditText? = null
    private var etFirstName: EditText?=null
    private var etLastName: EditText? = null
    private var etConfPassword: EditText? = null
    private  var activitytemp: Activity = activity

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {
        when (views.id) {
            R.id.et_email -> {
                etEmail = views as EditText;
                validateEmail(etEmail!!);
            }
            R.id.et_password -> {
                etPassword = views as EditText
                validatePassword(etPassword!!);
            }
            R.id.et_first_name -> {
                etFirstName = views as EditText
                validateFirstName(etFirstName!!);
            }
            R.id.et_last_name -> {
                etLastName = views as EditText
                validateLastName(etLastName!!);
            }
            R.id.et_confirm_password -> {
                etConfPassword = views as EditText
                validateConfirmPassword(etConfPassword!!,R.id.et_password as EditText);
            }
        }
    }

    private fun validateEmail(etEmail: EditText): Boolean {
        email = etEmail?.text.toString()

        if (email!!.isEmpty() || !isValidEmail(email!!)) {
            etEmail?.error = "Enter valid email address";
            etEmail?.let { requestFocus(it) };
            return false;
        }
        else
        {
            etEmail?.error = null
            return true;
        }

    }

    private fun  isValidEmail(email:String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            activitytemp.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private fun validatePassword(etPassword: EditText): Boolean {
        password = etPassword?.text.toString()
        if (password!!.trim().isEmpty()) {
            etPassword?.error= "Enter the password";
            etPassword?.let { requestFocus(it) };
            return false;
        }
        else
        {
            etPassword?.error = null
            return true;
        }
    }

    private fun validateFirstName(etName: EditText): Boolean {
        val name = etPassword?.text.toString()
        if (name!!.trim().isEmpty()) {
            etName?.error= "Enter the password";
            etName?.let { requestFocus(it) };
            return false;
        }
        else
        {
            etName?.error = null
            return true;
        }
    }

    private fun validateLastName(etName: EditText): Boolean {
        val name = etPassword?.text.toString()
        if (name!!.trim().isEmpty()) {
            etName?.error= "Enter the password";
            etName?.let { requestFocus(it) };
            return false;
        }
        else
        {
            etName?.error = null
            return true;
        }
    }

    private fun validateConfirmPassword(etconfpassword: EditText, etpassword: EditText): Boolean {
        val conPass = etconfpassword?.text.toString()
        val pass = etconfpassword?.text.toString()
        if (conPass!!.trim().isEmpty()) {
            etconfpassword?.error= "Confirm the password";
            etconfpassword?.let { requestFocus(it) };
            return false;
        }
        else if (conPass != pass) {
            etconfpassword?.error= "Does not match with password field";
            etconfpassword?.let { requestFocus(it) };
            return false;
        }
        else{
            etconfpassword?.error = null
            return true;
        }
    }
}