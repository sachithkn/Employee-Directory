package com.whiterabbittest.employeedirectory.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whiterabbittest.employeedirectory.utils.RoomModels

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM EmployeeDaoModel")
    fun getAll(): LiveData<List<RoomModels.EmployeeDaoModel>>

    @Query("SELECT * FROM EmployeeDaoModel WHERE :id=id")
    fun getEmployee(id:String): LiveData<RoomModels.EmployeeDaoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg employeeDaoModel: RoomModels.EmployeeDaoModel)
}