package com.example.myapplication.ui.page.map

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.backendset.BackendUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapViewModel : ViewModel() {
    // StateFlowで画像とローディング状態を管理
    private val _imageBitmap = MutableStateFlow<ImageBitmap?>(null)
    val imageBitmap: StateFlow<ImageBitmap?> = _imageBitmap

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // 画像をダウンロードする処理
    fun downloadMapImage(startX: Double, startY: Double, endX: Double, endY: Double) {
        // BackendUtilを使って画像ダウンロード
        BackendUtil().downloadImage(startX, startY, endX, endY, object : BackendUtil.ImageDownloadCallback {
            override fun onSuccess(bitmap: Bitmap) {
                // BitmapをImageBitmapに変換してStateにセット
                _imageBitmap.value = bitmap.asImageBitmap()
                _isLoading.value = false // ローディング終了
            }

            override fun onFailure(t: Throwable) {
                Log.e("MapViewModel", "이미지 다운로드에 실패했습니다.: ${t.message}")
                _isLoading.value = false // 失敗してもローディング終了
            }
        })
    }
}
