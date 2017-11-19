package com.calvo.carolina.e_waiter.utils

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import org.json.JSONArray

fun loadFragment(activity: AppCompatActivity, fragmentId: Int, fragment: Fragment)
{
    if (activity.findViewById<View>(fragmentId) != null) {
        if (activity.fragmentManager.findFragmentById(fragmentId) == null) {
            activity.fragmentManager.beginTransaction()
                    .add(fragmentId, fragment)
                    .commit()
        }
    }
}

fun JSONArray.toListOfArray(): List<String>
{
    return (0 until length())
            .map { get(it).toString() }
            .toMutableList()
}