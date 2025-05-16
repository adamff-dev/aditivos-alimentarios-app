package com.addev.aditivosalimentarios.model

import androidx.room.Embedded
import androidx.room.Relation

class AdditiveWithAltNames {
    @Embedded
    var additive: Additive? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "additive_id"
    )
    var altNames: List<AltName>? = null
}
