package com.dev.test.model

 import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrenciesHistory(
    @PrimaryKey val id: Int?=null,
    val from: String,
    val to: String,
    val value: String
)