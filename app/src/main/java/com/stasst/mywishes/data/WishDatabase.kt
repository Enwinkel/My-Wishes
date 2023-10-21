package com.stasst.mywishes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Wish::class],
    version = 1
)
abstract class WishDatabase: RoomDatabase() {
    abstract val dao: WishDao
}