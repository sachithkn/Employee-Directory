package com.whiterabbittest.employeedirectory.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whiterabbittest.employeedirectory.data.dao.EmployeeDao

@Database(entities = [RoomModels.EmployeeDaoModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeListDao(): EmployeeDao
}