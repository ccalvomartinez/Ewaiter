package com.calvo.carolina.e_waiter.models

data class Table(val name: String, val orders: MutableList<Order> = mutableListOf())
