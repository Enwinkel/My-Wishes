package com.stasst.mywishes.di

import android.app.Application
import androidx.room.Room
import com.stasst.mywishes.data.WishDatabase
import com.stasst.mywishes.data.WishRepository
import com.stasst.mywishes.data.WishRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): WishDatabase {
        return Room.databaseBuilder(
            app,
            WishDatabase::class.java,
            "wish_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: WishDatabase): WishRepository {
        return WishRepositoryImpl(db.dao)
    }
}