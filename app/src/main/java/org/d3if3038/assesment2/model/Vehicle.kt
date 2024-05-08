package org.d3if3038.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namePembeli: String,
    val namePenjual: String,
    val variant: String,
)