package com.example.pocketfridge

import android.app.Application
import com.google.android.material.color.DynamicColors

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Apply dynamic color(Material3).
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}