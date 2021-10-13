package com.mchew.atrestaurants.di

import android.content.Context
import com.mchew.atrestaurants.core.ImageManager
import com.mchew.atrestaurants.core.PermissionManager
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

    @Provides
    fun providePermissionManager(
        @ApplicationContext context: Context
    ): PermissionManager {
        return PermissionManager(context)
    }
}