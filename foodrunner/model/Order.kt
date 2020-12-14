package com.reazon.foodrunner.model
data class Order (
    val id:Int? = null,
    val item:String?= null,
    val cost:Int?= null,
    val placed:Boolean?= false,
    val time:String? = ""
)