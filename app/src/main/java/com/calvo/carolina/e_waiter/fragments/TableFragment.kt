package com.calvo.carolina.e_waiter.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.MenuLetter
import com.calvo.carolina.e_waiter.models.Order
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.models.Tables

/**
 * A placeholder fragment containing a simple view.
 */
class TableFragment : Fragment()
{
    private lateinit var _root: View
    private  val position_: Int by lazy { arguments?.getInt(ARG_TABLE_INDEX) ?: 0 }
    private lateinit var adapter: ArrayAdapter<Order>

    private var onAddDishButtonClickedListener_: OnAddDishButtonClickedListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        if (inflater != null)
        {
            _root = inflater.inflate(R.layout.fragment_table, container, false)
            setAddDishButton()
            setOrdersList()
        }

        return _root
    }
    private fun setAddDishButton()
    {
        val addDishButton  = _root.findViewById<View>(R.id.add_dish_button)
        addDishButton.setOnClickListener { view ->
            onAddDishButtonClickedListener_?.onAddDishButtonClicked(Tables[position_])
        }
    }

    private fun setOrdersList()
    {
        val list = _root.findViewById<ListView>(R.id.ft_orders_list)
        adapter = ArrayAdapter<Order>(activity, android.R.layout.simple_list_item_1, Tables[position_].orders)
        list.adapter = adapter

        // Nos enteramos de que se ha pulsado un elemento de la lista así:
        //list.setOnItemClickListener { parent, view, position, id ->
        //    // Aviso al listener
        //    onCitySelectedListener?.onCitySelected(Cities.get(position), position)
        //}
    }

    fun addOrderToList(order: Order)
    {
        adapter.add(order)
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
        onAddDishButtonClickedListener_ = null
    }

    private fun commonAttach(listener: Any?) {
        if (listener is OnAddDishButtonClickedListener) {
            onAddDishButtonClickedListener_ = listener
        }
    }
    interface OnAddDishButtonClickedListener
    {
        fun onAddDishButtonClicked(table: Table)
    }

    companion object
    {
        val ARG_TABLE_INDEX = "ARG_TABLE_INDEX"
        fun newInstance(tableIndex: Int): TableFragment
        {
            val arguments = Bundle()
            arguments.putInt(ARG_TABLE_INDEX, tableIndex)
            val fragment = TableFragment()
            fragment.arguments = arguments

            return fragment
        }
    }
}
