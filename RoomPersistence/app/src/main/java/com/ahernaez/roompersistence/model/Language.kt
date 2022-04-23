package com.ahernaez.roompersistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Language")
data class Language(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "language_id")
    val languageId: String,

    @ColumnInfo(name = "language_name")
    val languageName: String
)