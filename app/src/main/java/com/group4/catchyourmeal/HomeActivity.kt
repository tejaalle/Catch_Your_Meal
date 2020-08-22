package com.group4.catchyourmeal

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.group4.catchyourmeal.MapActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_homes.*
import kotlinx.android.synthetic.main.activity_main.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeActivity: AppCompatActivity() {
    private lateinit var geocoder:Geocoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homes)
        supportActionBar?.title="Catch Your Meal"
        /*DECLARING BOTTOM_MENU AND NAVIGATION GRAPH */
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                Log.i("getting current destination","${destination.label}")
                if(destination.label=="Home"){
                    linear.visibility = View.VISIBLE
                }
                if(destination.label == "Search"){
                    linear.visibility = View.GONE
                }
                if(destination.label == "shopping cartDao"){
                    linear.visibility = View.GONE
                }
                if(destination.label == "Profile"){
                    linear.visibility = View.GONE
                }
            }
        })
        val latitude = intent.getDoubleExtra("latitude",0.0)
        val longitude = intent.getDoubleExtra("longitude",0.0)

        if(latitude != 0.0 && longitude != 0.0){
            geocoder = Geocoder(this, Locale.getDefault())
            var address = geocoder.getFromLocation(latitude,longitude,1)
            val addre:String = address.get(0).getAddressLine(0)
            location.setText("$addre")}

        location.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                Log.i("name","buttonclicked")
                location_function()

            }

        })
        location.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                location_function()
            }
        })
        location_btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                Log.i("name","buttonclicked")
                location_function()

            }

        })



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun location_function(){

        Dexter.withActivity(this@HomeActivity).withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION).withListener(
            object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Log.i("name","permission Granted")
                    val intent = Intent(this@HomeActivity,MapActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken
                ) {
                    Log.i("name","permission ROTATIONAL")
                    token.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if(response.isPermanentlyDenied()){
                        Log.i("name","permission Denied")
                        val builder = android.app.AlertDialog.Builder(this@HomeActivity)
                        builder.setTitle("Permission Denied")
                            .setMessage("Permission to access location is permanently Denied. You need to go to the settings to allow the permission")
                            .setNegativeButton("CANCEL",null)
                            .setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    val intent = Intent()
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    intent.setData(Uri.fromParts("package",getPackageName(),null));
                                }
                            }).show()

                    }
                    else{
                        Toast.makeText(this@HomeActivity,"Permision Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }).check()
        finish()

    }
}