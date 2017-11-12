package com.calvo.carolina.e_waiter.models

import java.io.Serializable

data class Order(val dish: Dish, var notes: String = ""): Serializable
{
    override fun toString(): String
    {
        val notesString: String
         if (notes != "")
         {
             notesString = " *"
         }
         else
         {
             notesString = ""
         }
        return "${dish.name}${notesString}"
    }
}