package com.calvo.carolina.e_waiter.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.fragments.TableFragment
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.models.Tables
import com.calvo.carolina.e_waiter.utils.loadFragment

import kotlinx.android.synthetic.main.activity_table.*

class TableActivity : AppCompatActivity(), TableFragment.OnAddDishButtonClickedListener
{
    companion object {
        val EXTRA_TABLE_POSITION = "EXTRA_TABLE_POSITION"
        val REQ_MENU_ACTIVITY = 256

        fun intent(context: Context, tableIndex: Int) : Intent
        {
            val intent = Intent(context, TableActivity::class.java)
            intent.putExtra(EXTRA_TABLE_POSITION, tableIndex)
            return intent
        }
    }

    private val position_ : Int by lazy { intent.getIntExtra(EXTRA_TABLE_POSITION, 0) }
    private val table_ : Table by lazy { Tables[intent.getIntExtra(EXTRA_TABLE_POSITION, 0)] }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        setActionBar()
        loadFragment(this, R.id.at_fragment_table, TableFragment.newInstance(position_))

    }

    private fun setActionBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = table_.name
    }
    override fun onAddDishButtonClicked(table: Table)
    {
        startActivityForResult(MenuActivity.intent(this), REQ_MENU_ACTIVITY)
    }
}
