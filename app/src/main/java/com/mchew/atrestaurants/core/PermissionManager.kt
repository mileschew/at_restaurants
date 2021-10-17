package com.mchew.atrestaurants.core

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionManager(
    private val context: Context
) {

    private var onLocationPermissionResult: ((isAllowed: Boolean) -> Unit)? = null
    private var launcher: ActivityResultLauncher<String>? = null

    // Call before Activity starts
    fun registerForLocationPermission(activity: ComponentActivity) {
        launcher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onLocationPermissionResult?.invoke(it)
        }
    }

    // Prompt for for permission, then execute code block
    fun verifyLocationPermission(onComplete: (isAllowed: Boolean) -> Unit) {
        if (needsLocationPermission()) {
            promptForLocationPermission(onComplete)
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

    private fun promptForLocationPermission(onComplete: (isAllowed: Boolean) -> Unit) {
        onLocationPermissionResult = onComplete
        launcher?.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}