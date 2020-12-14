package com.reazon.foodrunner.model
data class User(
    var username:String? = null,
    var uid:String? = null,
    var address:String? = null,
    var email:String? = null,
    var phone:String? = null
)
/*
class User{
    constructor()

    private lateinit var name: String
    private lateinit var password: String
    private lateinit var address: String
    private lateinit var email: String

    constructor(
        name: String,
        password: String,
        address: String,
        email: String
    ) : this() {
        this.name = name
        this.password = password
        this.address = address
        this.email = email
    }
    fun getName():String?{
        return name
    }
    fun setName(name:String){
        this.name = name
    }

    fun getPassword():String?{
        return password
    }
    fun setPassword(password:String){
        this.password = password
    }

    fun getAddress():String?{
        return address
    }
    fun setAddress(address:String){
        this.address = address
    }

    fun getEmail():String?{
        return email
    }
    fun setEmail(email:String){
        this.email = email
    }


}*/
