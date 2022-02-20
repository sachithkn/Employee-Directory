package com.whiterabbittest.employeedirectory.data.webservice

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class WebServiceProvider {

    private var instance: WebService? = null
    fun getInstance(): WebService? {
        if (instance == null) {

            val baseUrl = "http://www.mocky.io/v2/"
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WebService::class.java)
        }
        return instance
    }

}