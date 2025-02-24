package com.example.fetchexercise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fetchexercise.model.ListItem

@Dao
interface ListDao {
    @Query("SELECT * FROM list_info")
    suspend fun getListInfo(): List<ListItem>

    @Insert
    suspend fun insertAll(listItem: List<ListItem>)
}