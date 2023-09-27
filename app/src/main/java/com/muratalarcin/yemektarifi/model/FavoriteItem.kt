package com.muratalarcin.yemektarifi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_items")
data class FavoriteItem(
    @PrimaryKey(autoGenerate = true) val itemId: Int,
    val specificationName: String?,
    val specificationTag: String?,
    val specificationMaterial: String?,
    val specificationFabrication: String?,
    val specificationImage: String?
)
