package com.mchew.atrestaurants.di

import android.content.Context
import com.mchew.atrestaurants.core.ImageManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AdapterEntryPoint {
    fun getImageManager(): ImageManager
}

fun Context.getAdapterImageManager(): ImageManager {
    return EntryPointAccessors.fromApplication(this, AdapterEntryPoint::class.java)
        .getImageManager()
}