package com.calvo.carolina.e_waiter.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.adapters.DishRecyclerViewAdapter

class MenuFragment : Fragment()
{

    private var onDishSelectedListener: OnDishSelectedListener? = null
    private lateinit var root_: View
    private lateinit var dishesList_: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        root_ = inflater.inflate(R.layout.fragment_menu, container, false)

        setDishesRecycleView()
        return root_
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
        onDishSelectedListener = null
    }

    private fun commonAttach(listener: Any?) {
        if (listener is OnDishSelectedListener) {
            onDishSelectedListener = listener
        }
    }
    private fun setDishesRecycleView()
    {
        dishesList_ = root_.findViewById(R.id.fm_dishes_menu_list)
        dishesList_.layoutManager = LinearLayoutManager(activity)
        dishesList_.itemAnimator = DefaultItemAnimator()
        dishesList_.adapter = DishRecyclerViewAdapter()
    }

    interface OnDishSelectedListener
    {
        fun onDishSelected(uri: Uri)
    }

    companion object
    {
        fun newInstance(): MenuFragment
        {
            val fragment = MenuFragment()
            return fragment
        }
    }
}// Required empty public constructor
