package com.calvo.carolina.e_waiter.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.models.Tables
import kotlinx.android.synthetic.main.fragment_table.*

/**
 * A placeholder fragment containing a simple view.
 */
class TableFragment : Fragment()
{
    private lateinit var root_: View
    private  val position_: Int by lazy { arguments?.getInt(ARG_TABLE_INDEX) ?: 0 }
    private var onAddDishButtonClickedListener_: OnAddDishButtonClickedListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        if (inflater != null)
        {
            root_ = inflater.inflate(R.layout.fragment_table, container, false)
            setAddDishButton()
        }

        return root_
    }
    private fun setAddDishButton()
    {
        val addDishButton  = root_.findViewById<View>(R.id.add_dish_button)
        addDishButton.setOnClickListener { view ->
            onAddDishButtonClickedListener_?.onAddDishButtonClicked(Tables[position_])
        }
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
