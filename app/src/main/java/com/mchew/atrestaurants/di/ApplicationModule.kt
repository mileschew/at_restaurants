package com.mchew.atrestaurants.di

import android.content.Context
import com.mchew.atrestaurants.core.ImageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun providesImageManager(
        @ApplicationContext context: Context
    ): ImageManager {
        return ImageManager(context)
    }
}