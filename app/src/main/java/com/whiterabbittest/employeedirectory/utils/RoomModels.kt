package com.whiterabbittest.employeedirectory.utils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

class RoomModels {
    @Entity
    data class EmployeeDaoModel(

        @NonNull
        @PrimaryKey
        var id: String,
        @ColumnInfo(name = "name")
        var name: String? = null,
        @ColumnInfo(name = "username")
        var username: String? = null,
        @ColumnInfo(name = "email")
        var email: String? = null,
        @ColumnInfo(name = "profile_image")
        var profile_image: String? = null,
        @ColumnInfo(name = "address")
        var address: String? = null,
        @ColumnInfo(name = "phone")
        var phone: String? = null,
        @ColumnInfo(name = "website")
        var website: String? = null,
        @ColumnInfo(name = "company")
        var company: String? = null,
    )

    @Entity
    data class CompanyDaoModel(
        @ColumnInfo(name = "name")
        var name: String? = null,
        @ColumnInfo(name = "catchPhrase")
        var catchPhrase: String? = null,
        @ColumnInfo(name = "bs")
        var bs: String? = null,
    )

    @Entity
    data class AddressDaoModel(
        @ColumnInfo(name = "street")
        var street: String? = null,
        @ColumnInfo(name = "suite")
        var suite: String? = null,
        @ColumnInfo(name = "city")
        var city: String? = null,
        @ColumnInfo(name = "zipcode")
        var zipcode: String? = null,
        @ColumnInfo(name = "geo")
        var geo: GeoDaoModel? = null,
    )

    @Entity
    data class GeoDaoModel(
        @ColumnInfo(name = "lat")
        var lat: String? = null,
        @ColumnInfo(name = "lng")
        var lng: String? = null
    )
}