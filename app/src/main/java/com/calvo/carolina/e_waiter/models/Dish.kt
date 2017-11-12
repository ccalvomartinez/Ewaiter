package com.calvo.carolina.e_waiter.models

import java.io.Serializable

data class Dish(val name: String, val price: Float, val image: String, val description: String, val hasAllergens: Boolean, val allergens: List<String> = listOf()): Serializable
{
}