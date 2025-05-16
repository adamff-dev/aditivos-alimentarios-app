package com.addev.aditivosalimentarios.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "additives")
class Additive {
    @PrimaryKey
    var id: Int = 0
    lateinit var code: String
    lateinit var name: String
    lateinit var toxicity: String
    var description: String? = null
    var uses: String? = null

    @ColumnInfo(name = "side_effects")
    var sideEffects: String? = null
}
