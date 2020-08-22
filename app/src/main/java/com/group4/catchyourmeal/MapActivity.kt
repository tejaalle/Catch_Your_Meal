package com.group4.catchyourmeal


import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.gesture.Prediction
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_map.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val REQUEST_PERMISSIONS_REQUEST_CODE= 34
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var predicttionList:List<AutocompletePrediction>
    private var mLastKnownLocation:Location? = null
    private  var mapView: View? = null
    private  var lat:Double? = null
    private var lon:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapView = mapFragment.view
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    val liveLocation = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(liveLocation).title("My location"))
                    val loc = CameraUpdateFactory.newLatLngZoom(liveLocation, 15f)
                    mMap.animateCamera(loc)
                }
            }
        }
        Places.initialize(this, getString(R.string.google_maps_key))
        val placesClient = Places.createClient(this)
        val token = AutocompleteSessionToken.newInstance()
        searchBar.setOnSearchActionListener(object :MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    searchBar.disableSearch()
                }            }

            override fun onSearchStateChanged(enabled: Boolean) {
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString(), true, null, true)            }
        })
        searchBar.addTextChangeListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val request = FindAutocompletePredictionsRequest.builder().setTypeFilter(TypeFilter.ADDRESS).setSessionToken(token).setQuery(s.toString()).build()
                placesClient.findAutocompletePredictions(request).addOnCompleteListener(
                    OnCompleteListener<FindAutocompletePredictionsResponse>{
                    @Override fun onComplete(@NonNull task: Task<FindAutocompletePredictionsResponse>) {
                        if (task.isSuccessful()) {
                            val predictionsResponse:FindAutocompletePredictionsResponse? = task.getResult()
                            if (predictionsResponse != null) {
                                 predicttionList = predictionsResponse.getAutocompletePredictions()
                                val suggestionsList =  ArrayList<String>()
                                for ( i in 0 until predicttionList.size) {
                                    val prediction: AutocompletePrediction = predicttionList.get(i);
                                    suggestionsList.add(prediction.getFullText(null).toString());
                                }
                                searchBar.updateLastSuggestions(suggestionsList);
                                if (!searchBar.isSuggestionsVisible()) {
                                    searchBar.showSuggestionsList();
                                }
                            }
                        } else {
                            Log.i("mytag", "prediction fetching task unsuccessful");
                        }
                    }
                })
            }


        })
        searchBar.setSuggestionsClickListener(object:SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemDeleteListener(position: Int, v: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun OnItemClickListener(position: Int, v: View?) {
                Log.i("teja","started")
                if (position >= predicttionList.size) {
                    return
                }
              var selectedPrediction:AutocompletePrediction = predicttionList.get(position)
                var suggestion:String = searchBar.getLastSuggestions().get(position).toString()
                searchBar.setText(suggestion)
                Handler().postDelayed(object :Runnable{
                    override fun run() {
                       searchBar.clearSuggestions()
                    }
                },1000)
                var imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if(imm!= null)
                    imm.hideSoftInputFromWindow(searchBar.windowToken,InputMethodManager.HIDE_IMPLICIT_ONLY)
                var placeId:String = selectedPrediction.placeId
                var placeFields:List<Place.Field> = Arrays.asList(Place.Field.LAT_LNG)
                var fetchPlaceRequest:FetchPlaceRequest = FetchPlaceRequest.builder(placeId,placeFields).build()
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(object :OnSuccessListener<FetchPlaceResponse>{
                    override fun onSuccess(p0: FetchPlaceResponse?) {
                        var place:Place? = p0?.place
                        Log.i("mytag","place found:" +place?.name)
                        var latLngOfPlace:LatLng? = place?.latLng
                        if(latLngOfPlace!= null){
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngOfPlace))

                        }
                    }
                }).addOnFailureListener(object :OnFailureListener{
                    override fun onFailure(p0: Exception) {
                     if( p0 is ApiException){
                      var apiException:ApiException = p0
                         apiException.printStackTrace()
                         var statusCode:Int = apiException.statusCode
                         Log.i("mytag","place not found:" +p0.message)
                         Log.i("mytag","status code:"+statusCode)
                     }
                    }
                })
            }
    })
    find.setOnClickListener {

        val intent = Intent(this,HomeActivity::class.java)
        intent.putExtra("latitude",lat)
        intent.putExtra("longitude",lon)
        startActivity(intent)
        finish()

    }
    }
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
            Looper.getMainLooper())
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode != REQUEST_PERMISSIONS_REQUEST_CODE) return
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener {
                    location ->
                if (location == null) {
                    createLocationRequest()
                    startLocationUpdates()
                }
            }).addOnFailureListener(this) { e -> Log.w(
                ContentValues.TAG,
                "getLastLocation:onFailure", e)}
        }
    }



    public override fun onStart() {
        super.onStart()
        if (!hasLocationPermissions()) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)

        }
        else {getAddress()
        }
    }
    override fun onResume() {
        super.onResume()
        createLocationRequest()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
    fun hasCoarseLocationPermission() = ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    fun hasLocationPermissions() = hasFineLocationPermission() && hasCoarseLocationPermission()
    fun hasFineLocationPermission() = ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun getAddress(){
        fusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener {
                location ->
            if (location != null) {
                location.latitude
                location.longitude
            }
        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMyLocationEnabled(true)
        mMap.uiSettings.setMyLocationButtonEnabled(true)

        var locationRequest:LocationRequest = LocationRequest.create()
        locationRequest.setInterval(10000)
        locationRequest.setFastestInterval(5000)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        var builder:LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        var settingClient:SettingsClient = LocationServices.getSettingsClient(this)
        var task:Task<LocationSettingsResponse> =  settingClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this,object :OnSuccessListener<LocationSettingsResponse>{
            override fun onSuccess(p0: LocationSettingsResponse?) {
                getDeviceLocation()
            }
        })
        task.addOnFailureListener(this,object :OnFailureListener{
            override fun onFailure(p0: Exception) {
                if( p0 is ResolvableApiException){
                    var resolvable:ResolvableApiException = p0
                    try {
                        resolvable.startResolutionForResult(this@MapActivity,51)
                    }catch (e1:IntentSender.SendIntentException){
                        e1.printStackTrace()
                    }
                }
            }
        })
        mMap.setOnMyLocationButtonClickListener(object :GoogleMap.OnMyLocationButtonClickListener{
            override fun onMyLocationButtonClick(): Boolean {
                if(searchBar.isSuggestionsVisible)
                    searchBar.clearSuggestions()
                if(searchBar.isSearchEnabled)
                    searchBar.disableSearch()
                return false
            }
        })
        // Add a marker in Sydney and move the camera

        //val myLoc = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(myLoc).title("My location"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 51){
            if(resultCode == Activity.RESULT_OK){
                getDeviceLocation()
            }
        }
    }



    private fun getDeviceLocation() {
     fusedLocationClient.lastLocation.addOnCompleteListener(object :OnCompleteListener<Location>{
         override fun onComplete(p0: Task<Location>) {
             if(p0.isSuccessful){
                  mLastKnownLocation = p0.result
                 if(mLastKnownLocation != null){
                     mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(mLastKnownLocation?.latitude?:0.0,mLastKnownLocation?.longitude?:0.0)))
                     lat = mLastKnownLocation?.latitude
                     lon = mLastKnownLocation?.longitude

                 }else{
                     var locationRequest:LocationRequest = LocationRequest.create()
                     locationRequest.interval
                     locationRequest.setFastestInterval(50000)
                     locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                     locationCallback = object : LocationCallback(){
                         override fun onLocationResult(p0: LocationResult?) {
                             super.onLocationResult(p0)
                             if(p0 == null)
                                 return
                             mLastKnownLocation = p0.lastLocation
                             mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(mLastKnownLocation?.latitude?:0.0,mLastKnownLocation?.longitude?:0.0)))
                             fusedLocationClient.removeLocationUpdates(locationCallback)
                         }

                     }
                     fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,null)
                      lat = mLastKnownLocation?.latitude
                     lon = mLastKnownLocation?.longitude

                 }
             }
             else{
                 Toast.makeText(this@MapActivity,"unable to get location", Toast.LENGTH_SHORT).show()
             }
         }
     })
 }



}