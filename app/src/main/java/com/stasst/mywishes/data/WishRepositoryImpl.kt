package com.stasst.mywishes.data
import kotlinx.coroutines.flow.Flow

class WishRepositoryImpl(
    private val dao: WishDao
): WishRepository {

    override suspend fun insertWish(todo: Wish) {
        dao.insertWish(todo)
    }

    override suspend fun deleteWish(todo: Wish) {
        dao.deleteWish(todo)
    }

    override suspend fun getWishById(id: Int): Wish? {
        return dao.getWishById(id)
    }

    override fun getWishes(): Flow<List<Wish>> {
        return dao.getWishes()
    }
}