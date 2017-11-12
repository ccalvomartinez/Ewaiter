package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.fragments.MenuFragment
import com.calvo.carolina.e_waiter.models.Dish
import com.calvo.carolina.e_waiter.models.Order
import com.calvo.carolina.e_waiter.utils.loadFragment

class MenuActivity : AppCompatActivity(), MenuFragment.OnDishSelectedListener
{
    companion object
    {
        val EXTRA_SELECTED_DISH = "EXTRA_SELECTED_DISH"
        fun intent(context: Context): Intent
        {
            return Intent(context, MenuActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //TODO("Conseguir que funcione el botón de atrás")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadFragment(this, R.id.am_menu_fragment, MenuFragment.newInstance())
    }
    override fun onDishSelected(dish: Dish)
    {
        Log.v("MY_LOG", "Menu activity. OnDishSelected Dish ${dish.toString()}")
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_SELECTED_DISH, dish)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
