package com.myexample.vitaltrack

import DB.Vital
import DB.VitalRepository
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

class VitalViewModel(private val repository: VitalRepository) : ViewModel() {
    val vitals = repository.vitals

    val inputSysBp = MutableLiveData<String>()
    val inputDiaBp = MutableLiveData<String>()
    val inputWeight = MutableLiveData<String>()
    val inputKicks = MutableLiveData<String>()
    val inputHeartRate = MutableLiveData<String>()

//    fun insertVital(){
//        val SysBp = inputSysBp.value!!
//        val DiaBp = inputDiaBp.value!!
//        val Weight = inputWeight.value!!
//        val Kicks = inputKicks.value!!
//        val heartRate = inputHeartRate.value!!
//        val sdf = SimpleDateFormat("EEE, dd MMM yyyy h:mm a", Locale.getDefault())
//        val currentDateTime = sdf.format(Date())!!
//        insert(Vital(0,SysBp,DiaBp,Weight,Kicks,currentDateTime,heartRate))
//        inputSysBp.value = ""
//        inputDiaBp.value = ""
//        inputWeight.value = ""
//        inputKicks.value = ""
//        inputHeartRate.value = ""
//    }

    fun insertVital() {
        val sysBp = inputSysBp.value?.trim().orEmpty()
        val diaBp = inputDiaBp.value?.trim().orEmpty()
        val weight = inputWeight.value?.trim().orEmpty()
        val kicks = inputKicks.value?.trim().orEmpty()
        val heartRate = inputHeartRate.value?.trim().orEmpty()

        // Check if at least one field has a value
        val isAtLeastOneFilled = listOf(sysBp, diaBp, weight, kicks, heartRate).any { it.isNotEmpty() }

        if (isAtLeastOneFilled) {
            val sdf = SimpleDateFormat("EEE, dd MMM yyyy h:mm a", Locale.getDefault())
            val currentDateTime = sdf.format(Date())

            insert(Vital(0, sysBp, diaBp, weight, kicks, currentDateTime, heartRate))

            // Clear inputs
            inputSysBp.value = ""
            inputDiaBp.value = ""
            inputWeight.value = ""
            inputKicks.value = ""
            inputHeartRate.value = ""
        } else {
            // show toast error if all empty
        }
    }


    fun insert(vital: Vital){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(vital)
        }
    }
}