package com.mchew.atrestaurants.core

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionManager(
    private val context: Context
) {

    fun verifyLocationPermission(activity: AppCompatActivity, onComplete: (isAllowed: Boolean) -> Any) {
        if (needsLocationPermission()) {
            promptForLocationPermission(activity, onComplete)
        } else {
            onComplete.invoke(true)
        }
    }

    private fun needsLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun promptForLocationPermission(activity: AppCompatActivity, onComplete: (isAllowed: Boolean) -> Any) {
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onComplete.invoke(it)
        }.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}