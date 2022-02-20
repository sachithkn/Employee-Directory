package com.whiterabbittest.employeedirectory.data.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.whiterabbittest.employeedirectory.data.repository.EmployeeRepository
import com.whiterabbittest.employeedirectory.utils.Models
import com.whiterabbittest.employeedirectory.utils.RoomModels

class EmployeeViewModel(private val employeeRepository:EmployeeRepository) {

    private var employeeList: LiveData<List<Models.Employee>>? = null

    fun getEmployeeList(lifecycleOwner: LifecycleOwner): LiveData<List<RoomModels.EmployeeDaoModel>>?{
        return employeeRepository.getEmployeeList(lifecycleOwner)
    }

    fun getEmployee(lifecycleOwner: LifecycleOwner,id:String): LiveData<RoomModels.EmployeeDaoModel>?{
        return employeeRepository.getEmployee(lifecycleOwner,id)
    }


    fun getEmployeeSearch(lifecycleOwner: LifecycleOwner,search:String): LiveData<List<RoomModels.EmployeeDaoModel>>?{
        return employeeRepository.getEmployeeSearch(lifecycleOwner,search)
    }
}