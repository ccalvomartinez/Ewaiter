package com.calvo.carolina.e_waiter.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.fragments.MenuFragment
import com.calvo.carolina.e_waiter.utils.loadFragment

class MenuActivity : AppCompatActivity()
{
    companion object
    {
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
}
