package com.addev.aditivosalimentarios.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "additives")
class Additive {
    @PrimaryKey
    var id: Int = 0
    var code: String? = null
    var name: String? = null
    var description: String? = null
    var uses: String? = null
    @ColumnInfo(name = "side_effects")
    var sideEffects: String? = null
    var toxicity: String? = null
}
