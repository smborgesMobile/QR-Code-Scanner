package com.br.smobile.com.result.repository

import com.br.smobile.com.result.database.ResultDAO
import com.br.smobile.com.result.model.ResultModel

interface ResultRepository {
    suspend fun insertNewResult(result: ResultModel): List<ResultModel>
    suspend fun getHistory(): List<ResultModel>
    suspend fun deleteResult(result: ResultModel): List<ResultModel>
}

class ResultRepositoryImp(private val resultAPI: ResultDAO) : ResultRepository {
    override suspend fun insertNewResult(result: ResultModel): List<ResultModel> {
        val oldHistory = getHistory()

        val containsItem = oldHistory.filter { it.text == result.text }

        if (containsItem.isEmpty()) {
            resultAPI.addItem(result)
        }
        return getHistory()
    }

    override suspend fun getHistory(): List<ResultModel> = resultAPI.getAllItems()

    override suspend fun deleteResult(result: ResultModel): List<ResultModel> {
        resultAPI.delete(result)
        return getHistory()
    }

}