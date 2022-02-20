package com.whiterabbittest.employeedirectory.utils

class Models {

    data class Employee(
        var id: String?,
        var name: String?,
        var username: String?,
        var email: String?,
        var profile_image: String?,
        var address: Address?,
        var phone: String?,
        var website: String?,
        var company: Company?
    )

    data class Company(
        var name: String?,
        var catchPhrase: String?,
        var bs: String?,
    )

    data class Address(
        var street: String?,
        var suite: String?,
        var bs: String?,
        var city: String?,
        var zipcode: String?,
        var geo: Geo?,
    )

    data class Geo(
        var lat: String?,
        var lng: String?,
    )
}