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

class TableActivity : AppCompatActivity()
{
    companion object {
        val EXTRA_TABLE = "EXTRA_TABLE"

        fun intent(context: Context, tableIndex: Int) : Intent
        {
            val intent = Intent(context, TableActivity::class.java)
            intent.putExtra(EXTRA_TABLE, tableIndex)
            return intent
        }
    }

    private lateinit var _table : Table

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        initModel()
        setActionBar()
        loadFragment(this, R.id.at_fragment_table, TableFragment.newInstance())

    }

    private fun initModel()
    {
        _table = Tables[intent.getIntExtra(EXTRA_TABLE, 0)]
    }

    private fun setActionBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = _table.name
    }
}
