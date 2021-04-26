package loginapp.homemodule.profilefragmentmodule

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import loginapp.demo.R
import loginapp.di.PreferenceStorage
import loginapp.homemodule.HomeViewModel
import loginapp.utili.DialogUtils
import javax.inject.Inject

@AndroidEntryPoint
@ActivityScoped
class ProfileMapsFragment : Fragment()  {
    private var mMap: GoogleMap?=null
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    @Inject
    lateinit var preferenceStorage: PreferenceStorage
    private val homeViewModel: HomeViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        mMap=googleMap
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!").icon(BitmapDescriptorFactory.fromResource(R.drawable.user))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentLocation.latitude, currentLocation.longitude), 5f))
        googleMap?.addMarker(markerOptions)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_maps, container, false)
    }
    private fun plotMarkeronMap(authToken: String) {
        homeViewModel.plotMarkeronMap(authToken)
            .observe(requireActivity(), androidx.lifecycle.Observer {
                DialogUtils.stopProgressDialog()
                if (it != null) {
                    for (i in it.indices) {
                        val latLng = LatLng(it.get(i).lat, it.get(i).lng)
                        mMap!!.addMarker(MarkerOptions().position(latLng).title(it.get(i).vehicleType).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_ico)))
                        mMap!!.getUiSettings().setZoomControlsEnabled(true)
                        //  mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    }
                } else {

                }
            })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(requireActivity())
        fetchLocation()
        lifecycleScope.launch {
            val authToken = preferenceStorage.token.firstOrNull()
            if (authToken != null) {
                DialogUtils.startProgressDialog(requireActivity())
                plotMarkeronMap(authToken)
            }
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(requireContext(), currentLocation.latitude.toString() + "" +
                        currentLocation.longitude, Toast.LENGTH_SHORT).show()
                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        when (requestCode) {
            permissionCode ->{
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                }
            }
        }
    }

}