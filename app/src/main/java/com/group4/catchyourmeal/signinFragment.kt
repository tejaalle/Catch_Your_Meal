package com.group4.catchyourmeal

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class signinFragment : Fragment() {

    private var firebaseDatabaseReference: DatabaseReference? = null
    private var fireabseDatabase: FirebaseDatabase? = null
    private var btnSignout: Button? = null

    //UI elements
    private var tvFirstName: TextView? = null
    private var tvLastName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvEmailVerifiied: TextView? = null

    val RC_SIGN_IN: Int = 1
    private var TAG = "SignInActivity"
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String? = null
    private var password: String? = null
    //UI elements

    private var forgotPasswordtv: TextView?= null
    private var etEmail: EditText?=null
    private var etPassword: EditText? = null
    private var googleButton: SignInButton? = null
    private var btnLogin: Button? = null
    private var createAccount: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var backBtn: ImageView? = null
    private var progressDialog: ProgressDialog?= null
    //Firebase references
    private var mAuth: FirebaseAuth? = null

    private var mContext: Context? = null
    private var mActivity: Activity? = null

    companion object {
        fun newInstance() = signinFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("myTag", "This is my message1");
        val view: View = inflater.inflate(R.layout.fragment_signin, container, false)
        mContext = context
        startLoginActivity(view)

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d("myTag", "This is my message2");

        Log.d("myTag", "This is my message3");

        firebaseAuth = FirebaseAuth.getInstance()

        configureGoogleSignIn()
        setupUI()
    }

    /*override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("myTag", "This is my message2");

        Log.d("myTag", "This is my message3");
        mContext = context
        firebaseAuth = FirebaseAuth.getInstance()

        configureGoogleSignIn()
        setupUI()
    }*/


    override fun onAttach(context: Context) {
        super.onAttach(context!!)
        if (context is Activity) {
            mActivity = context
        }
    }




    private fun startLoginActivity(view: View) {
        Log.d("myTag", "This is my message");
         forgotPasswordtv = view.findViewById<View>(R.id.forgot_Password) as TextView
         etEmail = view.findViewById<View>(R.id.etemailsignin) as EditText
         etPassword = view.findViewById<View>(R.id.etpasswordsignin) as EditText
         btnLogin = view.findViewById<View>(R.id.btnlogin) as Button
         googleButton = view.findViewById<View>(R.id.googlebutton) as SignInButton
         createAccount = view.findViewById<View>(R.id.link_Signup) as TextView
         mProgressBar = view.findViewById<View>(R.id.loading) as ProgressBar
         progressDialog = ProgressDialog(mContext)
        mAuth = FirebaseAuth.getInstance()
        backBtn = view.findViewById(R.id.back_arrow)

        Log.d("state", forgotPasswordtv!!.text.toString());
        forgotPasswordtv!!.setOnClickListener {
            Log.d(TAG, "click on forgot password")
            findNavController().navigate(R.id.navigation_forgot_password)}
        createAccount!!
            .setOnClickListener {
                Log.d(TAG, "click on forgot password")
                findNavController().navigate(R.id.navigation_sign_up)}
        btnLogin!!.setOnClickListener {
            Log.d(TAG, "click on forgot password")
            loginUser() }

        etEmail!!.addTextChangedListener(MyTextWatcher(etEmail!!, mActivity!!))
        etPassword!!.addTextChangedListener(MyTextWatcher(etPassword!!, mActivity!!))
        backBtn!!.setOnClickListener {
            Log.d(TAG, "click on back password")
            findNavController().navigate(R.id.navigation_profile)}




    }


    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, mGoogleSignInOptions) }!!
    }

    private fun setupUI() {
        googleButton!!.setOnClickListener {
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
                Toast.makeText(mContext, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.navigation_home)
            } else {
                Toast.makeText(mContext, "Google sign in failed:(", Toast.LENGTH_LONG).show()
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
                .addOnCompleteListener(mActivity!!) { t ->
                    hideProgressDialogWithTitle()
                    if (t.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information

                        Log.d(TAG, "signInWithEmail:success")

                        redirectToHomeActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", t.exception)
                        Toast.makeText(mContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(mContext, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }


    private fun redirectToHomeActivity() {
        findNavController().navigate(R.id.navigation_home)
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
            activity!!.parent.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
