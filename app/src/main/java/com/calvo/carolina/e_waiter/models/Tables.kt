package com.calvo.carolina.e_waiter.models

object Tables
{
    private val _tables: List<Table> = listOf(
        Table("Mesa 1"),
        Table("Mesa 2"),
        Table("Mesa 3"),
        Table("Mesa 4")
    )

    val count
        get() = _tables.size

     operator fun get(i: Int) = _tables[i]

    fun toArray() = _tables.toTypedArray()
}

