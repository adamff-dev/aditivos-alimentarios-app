package com.addev.aditivosalimentarios.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "additives")
class Additive {
    @PrimaryKey
    var id: Int? = 0
    var code: String? = null
    var name: String? = null
    var toxicity: String? = null
}
