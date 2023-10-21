package com.stasst.mywishes.data

import kotlinx.coroutines.flow.Flow

interface WishRepository {

    suspend fun insertWish(wish: Wish)

    suspend fun deleteWish(wish: Wish)

    suspend fun getWishById(id: Int): Wish?

    fun getWishes(): Flow<List<Wish>>
}