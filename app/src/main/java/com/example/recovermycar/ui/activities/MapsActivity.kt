package com.example.recovermycar.activities

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.recovermycar.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location




    private val LOCATION_PERMISSION_REQUEST_CODE = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient ( this )


    }

    /**
    * Manipula o mapa assim que disponível.
    * Este retorno de chamada é acionado quando o mapa está pronto para ser usado.
    * Aqui é onde podemos adicionar marcadores ou linhas, adicionar ouvintes ou mover a câmera. Nesse caso,
    * acabamos de adicionar um marcador perto de Sydney, Austrália.
    * Se o Google Play Services não estiver instalado no dispositivo, o usuário será solicitado a instalar
    * dentro do SupportMapFragment. Este método só será acionado quando o usuário tiver
    * instalou o Google Play Services e voltou ao aplicativo.
    */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener ( this )
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        val myPlace = LatLng ( 40.73 , - 73.99 )   // este é Nova York
//        map.addMarker(MarkerOptions().position(myPlace).title("Minha cidade favorita"))
//        map.moveCamera (CameraUpdateFactory.newLatLngZoom (myPlace, 12.0f ))
//
//        map.getUiSettings (). setZoomControlsEnabled ( true )
//        map.setOnMarkerClickListener ( this )
        setUpMap()
    }

    override  fun  onMarkerClick (p0: Marker ?) = false



    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private  fun  placeMarkerOnMap (location: LatLng ) {
        // 1
        val markerOptions = MarkerOptions(). position (location)

        val titleStr = getAddress(location)
        markerOptions.title(titleStr)

        // 2
//        markerOptions.icon (
//            BitmapDescriptorFactory.fromBitmap (
//            BitmapFactory.decodeResource (resources, R.mipmap.ic_user_location)))
        map.addMarker (markerOptions)

    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]

                    addressText +=  address.getAddressLine(0)

            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }
}