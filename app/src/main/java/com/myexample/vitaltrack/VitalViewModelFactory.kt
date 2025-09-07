package com.myexample.vitaltrack

import DB.VitalRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VitalViewModelFactory(private val repository:VitalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VitalViewModel::class.java)) {
            return VitalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}