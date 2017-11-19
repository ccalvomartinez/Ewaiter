package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.fragments.EditOrderFragment
import com.calvo.carolina.e_waiter.models.Order
import com.calvo.carolina.e_waiter.utils.loadFragment

class EditOrderActivity : AppCompatActivity(), EditOrderFragment.OnEditOrderListener
{
    companion object
    {
        val EXTRA_ORDER = "EXTRA_ORDER"
        val RETURNED_ORDER = "RETURNED_ORDER"
        val EDIT_MODE = "EDIT_MODE"
        val ORDER_POSITION = "ORDER_POSITION"

        fun intentAddOrder(context: Context, order: Order): Intent
        {
            val intent = Intent(context, EditOrderActivity::class.java)
            intent.putExtra(EXTRA_ORDER, order)
            intent.putExtra(EDIT_MODE, false)
            return intent
        }

        fun intentEditOrder(context: Context, order: Order, orderPosition: Int): Intent
        {
            val intent = Intent(context, EditOrderActivity::class.java)
            intent.putExtra(EXTRA_ORDER, order)
            intent.putExtra(EDIT_MODE, true)
            intent.putExtra(ORDER_POSITION, orderPosition)
            return intent
        }
    }

    private val _editMode by lazy { intent.getBooleanExtra(EDIT_MODE, false) }
    private val _orderPosition by lazy { intent.getIntExtra(ORDER_POSITION, -1) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_order)
        val order = intent.getSerializableExtra(EXTRA_ORDER) as Order
        val editMode = intent.getBooleanExtra(EDIT_MODE, false)
        loadFragment(this, R.id.aeo_edit_order_fragment, EditOrderFragment.newInstance(order, editMode))
    }

    override fun onAcceptChanges(order: Order)
    {
        val resultIntent = Intent()
        resultIntent.putExtra(RETURNED_ORDER, order)
        resultIntent.putExtra(EDIT_MODE, _editMode)
        resultIntent.putExtra(ORDER_POSITION, _orderPosition)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onCancelChanges(order: Order)
    {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
