package com.stasst.mywishes.data

import androidx.room.*
import com.stasst.mywishes.data.Wish
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWish(wish: Wish)

    @Delete
    suspend fun deleteWish(wish: Wish)

    @Query("SELECT * FROM wish WHERE id = :id")
    suspend fun getWishById(id: Int): Wish?

    @Query("SELECT * FROM wish")
    fun getWishes(): Flow<List<Wish>>
}