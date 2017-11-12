package com.calvo.carolina.e_waiter.models

data class Dish(val name: String, val price: Float, val image: String, val description: String, val hasAlergens: Boolean, val alergens: List<String> = listOf())
{
}