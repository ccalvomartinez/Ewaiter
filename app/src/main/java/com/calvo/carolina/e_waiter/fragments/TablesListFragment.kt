package com.calvo.carolina.e_waiter.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter

import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.models.Tables

class TablesListFragment : Fragment()
{
    private var onTableSelectedListener: OnTableSelectedListener? = null
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        if (inflater != null)
        {
            root = inflater.inflate(R.layout.fragment_tables_list, container, false)

            val list = root.findViewById<ListView>(R.id.ftl_table_list_view)
            //TODO("Mover todo esto a un ViewModel")
            val data = ArrayList<HashMap<String, Any>>()

            for (i in 0 until Tables.count)
            {
                val item = HashMap<String, Any>()
                item.put("name", Tables[i].name)
                item.put("orders", "Orders: ${Tables[i].orders.size}")
                data.add(item)
            }
            val adapter = SimpleAdapter(activity, data, android.R.layout.simple_list_item_2, arrayOf("name", "orders"), intArrayOf(android.R.id.text1, android.R.id.text2) )
            list.adapter = adapter

            // Nos enteramos de que se ha pulsado un elemento de la lista así:
            list.setOnItemClickListener { parent, view, position, id ->
                // Aviso al listener
                onTableSelectedListener?.onTableSelected(Tables.get(position), position)
            }
        }
        return root
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonAttach(context)
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        onTableSelectedListener = null
    }

    fun commonAttach(listener: Any?) {
        if (listener is OnTableSelectedListener) {
            onTableSelectedListener = listener
        }
    }

    interface OnTableSelectedListener
    {
        fun onTableSelected(table: Table, position: Int)
    }

    companion object
    {
        fun newInstance(): TablesListFragment
        {
            return TablesListFragment()
        }
    }
}
