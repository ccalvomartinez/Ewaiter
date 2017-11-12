package com.calvo.carolina.e_waiter.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Order
import kotlinx.android.synthetic.main.fragment_edit_order.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditOrderFragment.OnEditOrderListener] interface
 * to handle interaction events.
 * Use the [EditOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditOrderFragment : Fragment()
{
    private lateinit var _order: Order
    private lateinit var _root: View
    private var onEditOrderListener: OnEditOrderListener? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (arguments != null)
        {
            _order = arguments.getSerializable(ARG_ORDER) as Order
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        if (inflater != null)
        {
            _root = inflater.inflate(R.layout.fragment_edit_order, container, false)
            setUpOrder()
            setButtonListeners()
        }
        return _root
    }

    private fun setButtonListeners()
    {
        _root.findViewById<View>(R.id.feo_add_button).setOnClickListener { accepChanges() }
        _root.findViewById<View>(R.id.feo_cancel_button).setOnClickListener { cancelChanges() }
        Log.v("MY_TAG", "Click listeners")
    }

    private fun setUpOrder()
    {
        val imageView = _root.findViewById<ImageView>(R.id.feo_image_view)
        val descriptionText = _root.findViewById<TextView>(R.id.feo_description_texts)
        when (_order.dish.image)
        {
            "i01" -> imageView.setImageResource(R.drawable.i01)
            "i02" -> imageView.setImageResource(R.drawable.i02)
            "i03" -> imageView.setImageResource(R.drawable.i03)
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
        Log.v("MY_TAG", "Edit Order fragment. Accept changes ${_order}")
        onEditOrderListener?.onAcceptChanges(_order)
    }

    private fun cancelChanges()
    {
        onEditOrderListener?.onCancelChanges(_order)
    }

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        if (context is OnEditOrderListener)
        {
            onEditOrderListener = context
        }
        else
        {
            throw RuntimeException(context!!.toString() + " must implement onEditOrderListener")
        }
    }

    override fun onDetach()
    {
        super.onDetach()
        onEditOrderListener = null
    }

    interface OnEditOrderListener
    {
        fun onAcceptChanges(order: Order)
        fun onCancelChanges(order:Order)
    }

    companion object
    {
        private val ARG_ORDER = "ARG_ORDER"

        fun newInstance(order: Order): EditOrderFragment
        {
            val fragment = EditOrderFragment()
            val args = Bundle()
            args.putSerializable(ARG_ORDER, order)
            fragment.arguments = args
            return fragment
        }
    }
}
