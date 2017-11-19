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
import com.calvo.carolina.e_waiter.fragments.TablesListFragment
import com.calvo.carolina.e_waiter.models.Dish
import com.calvo.carolina.e_waiter.models.Tables
import com.calvo.carolina.e_waiter.utils.loadFragment

class MenuActivity : AppCompatActivity(), TablesListFragment.OnTableSelectedListener
{
    companion object
    {
        val EXTRA_SELECTED_DISH = "EXTRA_SELECTED_DISH"
        val RESULT_OTHER_TABLE_SELECTED = 345
        val EXTRA_SELECTED_TABLE_POSITION = "EXTRA_SELECTED_TABLE_POSITION"
        val EXTRA_INITIAL_TABLE_POSITION = "EXTRA_INITIAL_TABLE_POSITION "

        fun intent(context: Context, position: Int): Intent
        {
            val intent = Intent(context, MenuActivity::class.java)
            intent.putExtra(EXTRA_INITIAL_TABLE_POSITION, position)
            return intent
        }
    }

    private  var _position: Int = 0
    set(value)
    {
        field = value
        supportActionBar?.title = getString(R.string.menu_activity_menu_letter, Tables[value].name)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initializeLateInitVariables()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadFragment(this, R.id.am_table_list_fragment, TablesListFragment.newInstance())

        setDishesRecycleView()
    }

    override fun onTableSelected(position: Int)
    {
        _position = position
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_SELECTED_TABLE_POSITION, position)
        setResult(RESULT_OTHER_TABLE_SELECTED, returnIntent)
        finish()
    }

    private fun initializeLateInitVariables()
    {
        _position = intent.getIntExtra(EXTRA_INITIAL_TABLE_POSITION, 0)
    }

    private fun returnDishToParentActivityAndFinish(dish: Dish)
    {
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_SELECTED_DISH, dish)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
    private fun setDishesRecycleView()
    {
        val dishesList = findViewById<RecyclerView>(R.id.am_dishes_menu_list)
        dishesList.layoutManager = LinearLayoutManager(this)
        dishesList.itemAnimator = DefaultItemAnimator()
        val dishesAdapter = DishRecyclerViewAdapter()
        dishesAdapter.listener = object : DishRecyclerViewAdapter.OnDishSelectedListener
        {
            override fun onDishSelected(dish: Dish)
            {
                returnDishToParentActivityAndFinish(dish)
            }
        }
        dishesList.adapter = dishesAdapter

    }
}

