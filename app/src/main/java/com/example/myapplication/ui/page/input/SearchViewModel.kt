package com.example.myapplication.ui.page.input

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.backendset.BackendUtil
import com.example.myapplication.ui.backendset.data.Poi_
import com.example.myapplication.ui.backendset.data.SharedArrivalPoi
import com.example.myapplication.ui.backendset.data.SharedDepartPoi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {
    private val backendUtil = BackendUtil()

    // ユーザー入力用のクエリと結果リスト
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _filteredList = MutableStateFlow<List<Poi_>>(emptyList())
    val filteredList: StateFlow<List<Poi_>> = _filteredList

    // クエリを更新して検索実行
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        fetchPoiData(newQuery)
    }

    // BackendUtil からデータを取得してフィルタリング
    private fun fetchPoiData(query: String) {
        backendUtil.getPoiData(query, object : BackendUtil.PoiDataCallback {
            override fun onSuccess(poiList: List<Poi_>) {
                // 入力されたクエリに基づいて POI リストをフィルタリング
                _filteredList.value = poiList.filter { poi ->
                    poi.name.contains(query, ignoreCase = true)
                }
            }

            override fun onFailure(t: Throwable) {
                Log.e("SearchViewModel", "Failed to fetch POI data: ${t.message}")
                _filteredList.value = emptyList()
            }
        })
    }

    // 選択された POI を保存（緯度と経度を保存する）
    fun saveSelectedPoi(poi: Poi_, type: String) {
        // 緯度と経度も保存するロジックを追加
        Log.d("SearchViewModel", "Selected POI: ${poi.name}, Lat: ${poi.frontLat}, Lon: ${poi.frontLon}")
        if (type == "departure") {
            SharedDepartPoi.name = poi.name
            SharedDepartPoi.x = poi.frontLon
            SharedDepartPoi.y = poi.frontLat
            SharedDepartPoi.empty = false
        }
        else {
            SharedArrivalPoi.name = poi.name
            SharedArrivalPoi.x = poi.frontLon
            SharedArrivalPoi.y = poi.frontLat
            SharedArrivalPoi.empty = false
        }
    }

    // 検索を実行する関数
    fun performSearch(query: String) {
        fetchPoiData(query)
    }
}
