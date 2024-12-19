package com.example.myapplication.ui.backendset.data

import com.google.gson.annotations.SerializedName

data class PoiData(
    @SerializedName("searchPoiInfo") val searchPoiInfo: SearchPoiInfo
)

data class SearchPoiInfo(
    @SerializedName("pois") val pois: Pois
)

data class Pois(
    @SerializedName("poi") val poiList: List<Poi_>
)

data class Poi_(
    @SerializedName("name") val name: String,
    @SerializedName("frontLat") val frontLat: Double,
    @SerializedName("frontLon") val frontLon: Double
)


