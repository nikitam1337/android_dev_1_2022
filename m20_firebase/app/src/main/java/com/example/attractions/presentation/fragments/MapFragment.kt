package com.example.attractions.presentation.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.attractions.R
import com.example.attractions.data.model.Attraction
import com.example.attractions.databinding.FragmentMapBinding
import com.example.attractions.presentation.ViewModelFactory
import com.example.attractions.presentation.viewmodels.MapViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(), UserLocationObjectListener, CameraListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MapViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentMapBinding? = null
    private val binding
        get() = _binding!!

    private var saveLatitude = START_POINT.latitude
    private var saveLongitude = START_POINT.longitude
    private var saveZoom = 12.0f
    private var routeStartLocation = START_POINT
    private var followUserLocation = false

    private lateinit var mapView: MapView

    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var pinsCollection: MapObjectCollection

    private var currentPopupWindow: PopupWindow? = null

    private val placeMarkTapListener = MapObjectTapListener { mapObject, _ ->
        val attraction = mapObject.userData as Attraction
        showCustomBalloon(attraction)
        true
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                turnOnGps()
                onMapReady()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.required_permissions),
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapview

        mapView.mapWindow.map.move(
            START_POSITION
        )


        binding.location.setOnClickListener {
            followUserLocation = true
            if (checkOnGps()) {
                cameraUserPosition()
            } else {
                turnOnGps()
            }
        }

        binding.buttonZoomPlus.setOnClickListener { changeZoomButton(+ZOOM_STEP) }
        binding.buttonZoomMinus.setOnClickListener { changeZoomButton(-ZOOM_STEP) }

        if (savedInstanceState != null) {
            saveLatitude = savedInstanceState.getDouble("latitude")
            saveLongitude = savedInstanceState.getDouble("longitude")
            saveZoom = savedInstanceState.getFloat("zoom")
            routeStartLocation = Point(saveLatitude, saveLongitude)
            cameraSavePosition(saveZoom)
        }


    }

    private fun cameraUserPosition() {
        if (userLocationLayer.cameraPosition() != null && followUserLocation) {
            routeStartLocation = userLocationLayer.cameraPosition()!!.target
            binding.mapview.mapWindow.map.move(
                CameraPosition(routeStartLocation, 16f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    }

    private fun cameraSavePosition(saveZoom: Float) {
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(saveLatitude, saveLongitude),
                saveZoom,
                0f,
                0f
            )
        )
    }


    private fun checkPermission() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            turnOnGps()
            if (checkOnGps()) {
                onMapReady()
            }
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    private fun turnOnGps() {
        if (!checkOnGps()) {
            AlertDialog.Builder(context)
                .setMessage(getString(R.string.ask_turn_GPS_network))
                .setPositiveButton(
                    R.string.open_location_settings
                ) { _, _ ->
                    requireContext().startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                }
                .setNegativeButton(R.string.Cancel) { _, _ ->
                    parentFragmentManager.popBackStack()
                }
                .show()
        }
    }

    private fun checkOnGps(): Boolean {
        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (_: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (_: Exception) {
        }
        return gpsEnabled && networkEnabled
    }


    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()
        try {
            userLocationLayer = mapKit.createUserLocationLayer(binding.mapview.mapWindow)
            userLocationLayer.isVisible = true
            userLocationLayer.setObjectListener(this)
            mapView.mapWindow.map.addCameraListener(this)
        } catch (e: RuntimeException) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }

        mapView.mapWindow.map.addInputListener(object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                // Закрываем всплывающее окно при нажатии на карту
                currentPopupWindow?.dismiss()
            }

            override fun onMapLongTap(map: Map, point: Point) {}
        })


        viewModel.listAttractions.onEach { listAttractions ->
            val imageProvider =
                ImageProvider.fromResource(requireContext(), R.drawable.mark)

            pinsCollection = mapView.mapWindow.map.mapObjects.addCollection()
            listAttractions.forEach { attraction ->
                pinsCollection.addPlacemark().apply {
                    geometry = attraction.point
                    setIcon(imageProvider)
                    userData = attraction
                    addTapListener(placeMarkTapListener)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showCustomBalloon(attraction: Attraction) {

        currentPopupWindow?.dismiss()

        // Создаем всплывающее окно с пользовательским представлением
        val balloonView = LayoutInflater.from(context).inflate(R.layout.balloon_layout, null)
        val title = balloonView.findViewById<TextView>(R.id.balloon_title)
        val description = balloonView.findViewById<TextView>(R.id.balloon_description)

        title.text = attraction.name
        description.text = attraction.description

        // Отображаем всплывающее окно
        val popupWindow = PopupWindow(
            balloonView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        // Сохраняем всплывающее окно для последующего закрытия
        currentPopupWindow = popupWindow

        val closeButton = balloonView.findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun changeZoomButton(value: Float) {
        with(mapView.mapWindow.map.cameraPosition) {
            mapView.mapWindow.map.move(
                CameraPosition(target, zoom + value, azimuth, tilt),
                SMOOTH_ANIMATION,
                null,
            )
        }
    }


    override fun onStart() {
        super.onStart()
        checkPermission()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAnchor() {
        userLocationLayer.setAnchor(
            PointF(
                (mapView.width * 0.5).toFloat(), (mapView.height * 0.5).toFloat()
            ),
            PointF(
                (mapView.width * 0.5).toFloat(), (mapView.height * 0.83).toFloat()
            )
        )
        followUserLocation = false
    }

    private fun noAnchor() {
        userLocationLayer.resetAnchor()
    }

    override fun onObjectAdded(p0: UserLocationView) {
        if (followUserLocation) {
            setAnchor()
        }
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }

    override fun onCameraPositionChanged(
        map: Map,
        cPos: CameraPosition,
        cUpd: CameraUpdateReason,
        finish: Boolean
    ) {
        if (finish) {
            if (followUserLocation) {
                setAnchor()
            }
        } else {
            if (!followUserLocation) {
                noAnchor()
                saveLatitude = cPos.target.latitude
                saveLongitude = cPos.target.longitude
                saveZoom = cPos.zoom
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latitude", saveLatitude)
        outState.putDouble("longitude", saveLongitude)
        outState.putFloat("zoom", saveZoom)
    }

    companion object {
        private val START_POINT = Point(59.220496, 39.891517)
        private val START_POSITION = CameraPosition(START_POINT, 12.0f, 0f, 0f)

        private const val ZOOM_STEP = 1f
        private val SMOOTH_ANIMATION = Animation(Animation.Type.SMOOTH, 0.5f)

        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(android.Manifest.permission.ACCESS_FINE_LOCATION)
            add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }.toTypedArray()
    }
}
