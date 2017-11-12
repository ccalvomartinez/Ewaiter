package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
        fun intent(context: Context, order: Order): Intent
        {
            val intent = Intent(context, EditOrderActivity::class.java)
            intent.putExtra(EXTRA_ORDER, order)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_order)
        val order = intent.getSerializableExtra(EXTRA_ORDER) as Order

        loadFragment(this, R.id.aeo_edit_order_fragment, EditOrderFragment.newInstance(order))
    }

    override fun onAcceptChanges(order: Order)
    {
        val resultIntent = Intent()
        resultIntent.putExtra(RETURNED_ORDER, order)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onCancelChanges(order: Order)
    {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
