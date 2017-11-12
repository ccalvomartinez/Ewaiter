package com.calvo.carolina.e_waiter.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.R.id.fab

/**
 * A placeholder fragment containing a simple view.
 */
class TableFragment : Fragment()
{
    private lateinit var _root: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {

        _root = inflater.inflate(R.layout.fragment_table, container, false)
        val fab = _root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        return _root
    }

    companion object
    {
        fun newInstance(): TableFragment
        {
            return TableFragment()
        }
    }
}
