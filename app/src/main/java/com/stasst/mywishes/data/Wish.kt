package com.stasst.mywishes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wish(
    val wishText: String,
    val isFulfilled: Boolean,
    @PrimaryKey val id: Int? = null
)
