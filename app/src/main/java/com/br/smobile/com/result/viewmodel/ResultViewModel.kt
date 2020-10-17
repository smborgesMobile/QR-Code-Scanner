package com.br.smobile.com.result.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.smobile.com.result.model.ResultModel
import com.br.smobile.com.result.repository.ResultRepository
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {

    private val _results = MutableLiveData<ResultState>()
    fun observeResults(): LiveData<ResultState> = _results

    fun insertResult(model: ResultModel) {
        viewModelScope.launch {
            try {
                val result = repository.insertNewResult(model)
                _results.value = ResultState.Success(result)
            } catch (e: Exception) {
                _results.value = ResultState.Error
            }
        }
    }

    fun getAllResults() {
        viewModelScope.launch {
            try {
                val result = repository.getHistory()
                _results.value = ResultState.Success(result)
            } catch (e: java.lang.Exception) {
                _results.value = ResultState.Error
            }
        }
    }

    fun deleteResult(model: ResultModel) {
        viewModelScope.launch {
            try {
                val result = repository.deleteResult(model)
                _results.value = ResultState.Success(result)
            } catch (e: java.lang.Exception) {
                _results.value = ResultState.Error
            }
        }
    }

    sealed class ResultState {
        object Error : ResultState()
        class Success(val list: List<ResultModel>) : ResultState()
    }
}