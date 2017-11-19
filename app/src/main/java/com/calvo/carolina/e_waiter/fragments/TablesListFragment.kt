package com.calvo.carolina.e_waiter.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Tables

class TablesListFragment : Fragment()
{
    companion object
    {
        fun newInstance(): TablesListFragment
        {
            return TablesListFragment()
        }
    }

    private var _onTableSelectedListener: OnTableSelectedListener? = null
    private lateinit var _root: View
    private var _data = ArrayList<HashMap<String, Any>>()
    private lateinit var _adapter: SimpleAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        if (inflater != null)
        {
            _root = inflater.inflate(R.layout.fragment_tables_list, container, false)

            val list = _root.findViewById<ListView>(R.id.ftl_table_list_view)
            calculateData()
            _adapter = SimpleAdapter(activity, _data, android.R.layout.simple_list_item_2, arrayOf("name", "orders"), intArrayOf(android.R.id.text1, android.R.id.text2) )
            list.adapter = _adapter

            list.setOnItemClickListener { _, _, position, _ ->
                _onTableSelectedListener?.onTableSelected(position)
            }
        }
        return _root
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
        _onTableSelectedListener = null
    }

    fun onDataChanged()
    {
        calculateData()
        _adapter.notifyDataSetInvalidated()
    }

    private fun calculateData()
    {
        _data.clear()

        for (i in 0 until Tables.count)
        {
            val item = HashMap<String, Any>()
            item.put("name", Tables[i].name)
            item.put("orders", "Orders: ${Tables[i].orders.size}")
            _data.add(item)
        }
    }

    private fun commonAttach(listener: Any?) {
        if (listener is OnTableSelectedListener) {
            _onTableSelectedListener = listener
        }
    }

    interface OnTableSelectedListener
    {
        fun onTableSelected(position: Int)
    }

}