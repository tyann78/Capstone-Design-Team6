package com.example.myapplication.ui.backendset.data

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SharedContext: Application()  {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}