package com.dicoding.capstonemd.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.dicoding.capstonemd.Result
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstonemd.adapter.RestaurantAdapter
import com.dicoding.capstonemd.databinding.FragmentTabMapsBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class TabMapsFragment() : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentTabMapsBinding

    private val tabMapsViewModel by viewModels<TabMapsViewModel> {
        ViewModelFactory.getInstance(
            requireContext().applicationContext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Check location permissions before inflating layout
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            // Inflate layout if permissions are granted
            binding = FragmentTabMapsBinding.inflate(inflater, container, false)
            return binding.root
        } else {
            // Request permissions if not granted
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString(ARG_NAME)

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            getMyLastLocation(name!!)

            //rv adapter stuff
            val restaurantAdapter = RestaurantAdapter()
            binding.rvRestaurant.layoutManager = LinearLayoutManager(requireContext())
            binding.rvRestaurant.adapter = restaurantAdapter

            // Observe the LiveData

            val pref = UserPreference.getInstance(requireContext().applicationContext.dataStore)
            viewLifecycleOwner.lifecycleScope.launch {
                pref.isHiddenGem().collect { isHiddenGem: Boolean ->
                    if (isHiddenGem) {
                        // Call method for hidden gem restaurants
                        tabMapsViewModel.fetchHiddenGemRestaurantsByCategory(name)
                    } else {
                        // Call method for regular restaurants
                        tabMapsViewModel.fetchRestaurantsByCategory(name)
                    }
                }
            }

            tabMapsViewModel.restaurantData.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        restaurantAdapter.submitList(result.data)
                        showLoading(false)
                    }

                    is Result.Error -> {
                        // Handle error state if needed
                        showToast("ERROR STATE: ${result.data}")
                        Log.e("TabMapsFragment", "Error: ${result.data}")
                        showLoading(false)
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (view != null) {
                if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                ) {
                } else {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setTitle("Location Required")
                        .setMessage("This feature requires access to your location to function properly. Please enable location access in your settings.")
                        .setPositiveButton("Open Settings") { _, _ ->
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).let {
                                viewLifecycleOwner.lifecycleScope.launch {
                                    requireActivity().startActivity(it)
                                }
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            // Handle user canceling the dialog
                        }
                        .create()
                    dialog.show()
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation(name: String) {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    handleLocation(location, name)
                } else {
                    // Location not found, hide UI elements
                    showToast("Location Not Found")
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun handleLocation(location: Location, name: String) {
        val latitude = location.latitude.toString()
        val longitude = location.longitude.toString()

        tabMapsViewModel.fetchRestaurantData(name, latitude, longitude, 2000)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_NAME = "name"
        fun newInstance(name: String?): TabMapsFragment {
            val fragment = TabMapsFragment()
            val bundle = Bundle().apply {
                putString(ARG_NAME, name)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
