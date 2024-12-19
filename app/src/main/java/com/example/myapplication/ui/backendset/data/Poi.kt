package com.example.myapplication.ui.backendset.data

data class Poi(
    val name: String,
    val lat: Double,
    val lon: Double
)

val poiData = listOf(
    Poi("중앙대학교 서울캠퍼스", 37.50671079, 126.95841343),
    Poi("중앙대학교 서울캠퍼스 정문", 37.50671079, 126.95841343)
)
