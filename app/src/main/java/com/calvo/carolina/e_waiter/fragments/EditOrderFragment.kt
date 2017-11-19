package com.calvo.carolina.e_waiter.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Order
import kotlinx.android.synthetic.main.fragment_edit_order.view.*

class EditOrderFragment : Fragment()
{
    companion object
    {
        private val ARG_ORDER = "ARG_ORDER"
        private val EDIT_MODE = "EDIT_MODE"
        fun newInstance(order: Order, editMode: Boolean): EditOrderFragment
        {
            val fragment = EditOrderFragment()
            val args = Bundle()
            args.putSerializable(ARG_ORDER, order)
            args.putBoolean(EDIT_MODE, editMode)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var _order: Order
    private var _editMode: Boolean = false
    private lateinit var _root: View
    private var _onEditOrderListener: OnEditOrderListener? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (arguments != null)
        {
            _order = arguments.getSerializable(ARG_ORDER) as Order
            _editMode = arguments.getBoolean(EDIT_MODE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        if (inflater != null)
        {
            _root = inflater.inflate(R.layout.fragment_edit_order, container, false)
            setUpOrder()
            setButtonListeners()
        }
        return _root
    }

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        commonAttach(context)
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?)
    {
        super.onAttach(activity)
        commonAttach(activity)
    }

    override fun onDetach()
    {
        super.onDetach()
        _onEditOrderListener = null
    }

    private fun setButtonListeners()
    {
        _root.findViewById<View>(R.id.feo_add_button).setOnClickListener { accepChanges() }
        _root.findViewById<View>(R.id.feo_cancel_button).setOnClickListener { cancelChanges() }
    }

    private fun setUpOrder()
    {
        if (_editMode)
        {
            _root.findViewById<Button>(R.id.feo_add_button).text = getString(R.string.edit_dish_guardar)
            _root.findViewById<TextView>(R.id.feo_notes_text).text = _order.notes
        }
        val imageView = _root.findViewById<ImageView>(R.id.feo_image_view)
        val descriptionText = _root.findViewById<TextView>(R.id.feo_description_texts)
        when (_order.dish.image)
        {
            "i01" -> imageView.setImageResource(R.drawable.i01)
            "i02" -> imageView.setImageResource(R.drawable.i02)
            "i03" -> imageView.setImageResource(R.drawable.i03)
            "i04" -> imageView.setImageResource(R.drawable.i04)
            "i05" -> imageView.setImageResource(R.drawable.i05)
        }
        descriptionText.text = _order.dish.description

        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            supportActionBar?.title = _order.dish.name
        }
        for (i in 0 until _order.dish.allergens.size)
        {
            when( _order.dish.allergens[i])
            {
                "a01" -> _root.feo_gluten_01.visibility = View.VISIBLE
                "a02" -> _root.feo_egg_02.visibility = View.VISIBLE
                "a03" -> _root.feo_fish_03.visibility = View.VISIBLE
                "a04" -> _root.feo_milk_04.visibility = View.VISIBLE
                "a05" -> _root.feo_nuts_05.visibility = View.VISIBLE
                "a06" -> _root.feo_seafood_06.visibility = View.VISIBLE
            }
        }
    }

    private fun accepChanges()
    {
        _order.notes = _root.findViewById<TextView>(R.id.feo_notes_text).text.toString()
        _onEditOrderListener?.onAcceptChanges(_order)
    }

    private fun cancelChanges()
    {
        _onEditOrderListener?.onCancelChanges(_order)
    }

    private fun commonAttach(listener: Any?)
    {
        if (listener is OnEditOrderListener)
        {
            _onEditOrderListener = listener
        }
    }

    interface OnEditOrderListener
    {
        fun onAcceptChanges(order: Order)
        fun onCancelChanges(order:Order)
    }

}
