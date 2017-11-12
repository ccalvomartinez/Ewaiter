package com.calvo.carolina.e_waiter.utils

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import org.json.JSONArray

fun loadFragment(activity: AppCompatActivity, fragmentId: Int, fragment: Fragment)
{
    // Comprobamos que en la interfaz tenemos un FrameLayout llamado city_list_fragment
    if (activity.findViewById<View>(fragmentId) != null) {
        // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
        if (activity.fragmentManager.findFragmentById(fragmentId) == null) {
            activity.fragmentManager.beginTransaction()
                    .add(fragmentId, fragment)
                    .commit()
        }
    }
}

fun JSONArray.toListOfArray(): List<String>
{
    val result: MutableList<String> = mutableListOf()

    for (i in 0 until this.length())
    {
        val item = this.get(i).toString()
        result.add(item)
    }
    return result
}