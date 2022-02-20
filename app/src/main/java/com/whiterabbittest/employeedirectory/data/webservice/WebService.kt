package com.whiterabbittest.employeedirectory.data.webservice

import com.whiterabbittest.employeedirectory.utils.Models
import io.reactivex.Observable
import retrofit2.http.GET

interface WebService {

    @GET("5d565297300000680030a986")
    fun getEmployeeList(): Observable<List<Models.Employee>>

}