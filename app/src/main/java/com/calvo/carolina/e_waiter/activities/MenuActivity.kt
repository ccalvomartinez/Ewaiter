package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.adapters.DishRecyclerViewAdapter
import com.calvo.carolina.e_waiter.models.Dish

class MenuActivity : AppCompatActivity()
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.menu_activity_menu_letter)
        setDishesRecycleView()
    }

    private fun returnDish(dish: Dish)
    {
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_SELECTED_DISH, dish)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun setDishesRecycleView()
    {
        val dishesList_ = findViewById<RecyclerView>(R.id.fm_dishes_menu_list)
        dishesList_.layoutManager = LinearLayoutManager(this)
        dishesList_.itemAnimator = DefaultItemAnimator()
        val dishesAdapter = DishRecyclerViewAdapter()
        dishesAdapter.listener = object : DishRecyclerViewAdapter.OnDishSelectedListener
        {
            override fun onDishSelected(dish: Dish)
            {
                returnDish(dish)
            }
        }
        dishesList_.adapter = dishesAdapter

    }
}

