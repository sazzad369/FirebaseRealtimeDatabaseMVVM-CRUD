package com.example.firebaserealtimedatabasemvvm_crud.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaserealtimedatabasemvvm_crud.model.Data
import com.example.firebaserealtimedatabasemvvm_crud.repository.DataRepository


class DataViewModel: ViewModel() {

    private val dataRepository = DataRepository()
    private var _dataList:MutableLiveData<List<Data>> =  dataRepository.fetchData()
    val dataList:LiveData<List<Data>> get()  = _dataList


    private val _error = MutableLiveData<String?>(null)
    val error:LiveData<String?> get() = _error


    fun addData(data: Data,onSucces:()-> Unit, onFailure:()-> Unit) {

        dataRepository.addData(data)
            .addOnSuccessListener { onSucces() }
            .addOnFailureListener {
                _error.value = it.message
                onFailure()
            }

    }

    fun updateData(data: Data){
        dataRepository.updateData(data)
    }

    fun deleteData(data: Data, onSucces: () -> Unit, onFailure: () -> Unit){
        dataRepository.deleteData(data)
        onSucces()
    }

    fun handleDatabaseError(errorMessage:String?){
        _error.value = errorMessage
    }

}