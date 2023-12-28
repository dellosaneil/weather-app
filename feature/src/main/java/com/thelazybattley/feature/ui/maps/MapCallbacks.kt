package com.thelazybattley.feature.ui.maps

import com.google.android.gms.maps.model.LatLng

interface MapCallbacks {

    fun onSearchBarChange(name: String)

    fun onCoordinatesSelected(coordinates: LatLng)

    fun onSaveAddress()

}
