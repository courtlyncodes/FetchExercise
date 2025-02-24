package com.example.fetchexercise.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_info")
data class ListItem(
    @PrimaryKey(autoGenerate = true) val dbId: Int = 0,
    val id: Int,
    val listId: Int,
    val name: String?
)