package com.br.smobile.com.result.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.br.smobile.com.result.model.ResultModel

@Dao
interface ResultDAO {
    @Insert
    suspend fun addItem(item: ResultModel)

    @Query("SELECT * FROM ResultModel")
    suspend fun getAllItems(): List<ResultModel>

    @Delete
    suspend fun delete(item: ResultModel)
}