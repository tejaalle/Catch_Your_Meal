package com.group4.catchyourmeal

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
//import com.group4.catchyourmeal.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 1
    private val TAG = "SignInActivity"
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String? = null
    private var password: String? = null
    //UI elements

    private var forgotPasswordtv: TextView?= null
    private var etEmail: EditText?=null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var createAccount: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var progressDialog: ProgressDialog?= null
    //Firebase references
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        startLoginActivity()
        configureGoogleSignIn()
        setupUI()

    }

    private fun startLoginActivity() {
        forgotPasswordtv = findViewById<View>(R.id.forgot_Password) as TextView
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.btn_login) as Button
        createAccount = findViewById<View>(R.id.linkSignup) as TextView
        mProgressBar = findViewById<View>(R.id.loading) as ProgressBar
        progressDialog = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        forgotPassword.setOnClickListener { startActivity(
            Intent(this,
                ForgotPasswordActivity::class.java))}
        linkSignup
            .setOnClickListener { startActivity(Intent(this,
                CreateAccountActivity::class.java)) }
        btnLogin!!.setOnClickListener { loginUser() }

        etEmail!!.addTextChangedListener(MyTextWatcher(etEmail!!,this))
        etPassword!!.addTextChangedListener(MyTextWatcher(etPassword!!,this))




    }


    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI() {
        google_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            showProgressDialogWithTitle("Login ", "Hold on we are trying to login")
            Log.d(TAG, "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { t ->
                    hideProgressDialogWithTitle()
                    if (t.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information

                        Log.d(TAG, "signInWithEmail:success")

                        redirectToHomeActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", t.exception)
                        Toast.makeText(this@SignInActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }
    private fun redirectToMainActivity() {
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun redirectToHomeActivity() {
        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

     private fun validateEmail(): Boolean {
         email = etEmail?.text.toString()

        if (email!!.isEmpty() || !isValidEmail(email!!)) {
            etEmail?.error = getString(R.string.err_msg_email);
            etEmail?.let { requestFocus(it) };
            return false;
        }
        return true;
    }

    private fun  isValidEmail(email:String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

     private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private fun validatePassword(): Boolean {
        password = etPassword?.text.toString()
        if (password!!.trim().isEmpty()) {
            etPassword?.error= getString(R.string.err_msg_password);
            etPassword?.let { requestFocus(it) };
            return false;
        }
        return true;
    }

    // Method to show Progress bar
    private fun showProgressDialogWithTitle(title: String,substring:String) {
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









}
