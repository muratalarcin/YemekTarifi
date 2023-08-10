package com.muratalarcin.yemektarifi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Specification(
    val specificationName: String?,
    val specificationTag: String?,
    val specificationMaterial: String?,
    val specificationFabrication: String?,
    val specificationImage: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}