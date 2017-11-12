package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Dish
import com.calvo.carolina.e_waiter.models.Order
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.models.Tables

import kotlinx.android.synthetic.main.activity_table.*

class TableActivity : AppCompatActivity()
{
    companion object {
        val EXTRA_TABLE_POSITION = "EXTRA_TABLE_POSITION"
        val REQ_MENU_ACTIVITY = 256
        val REQ_EDIT_ORDER_ACTIVITY = 232

        fun intent(context: Context, tableIndex: Int) : Intent
        {
            val intent = Intent(context, TableActivity::class.java)
            intent.putExtra(EXTRA_TABLE_POSITION, tableIndex)
            return intent
        }
    }

    private val position_ : Int by lazy { intent.getIntExtra(EXTRA_TABLE_POSITION, 0) }
    private val table_ : Table by lazy { Tables[intent.getIntExtra(EXTRA_TABLE_POSITION, 0)] }
    private lateinit var adapter: ArrayAdapter<Order>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        setActionBar()
        setAddDishButton()
        setOrdersList()
    }

    private fun setActionBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = table_.name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_MENU_ACTIVITY && resultCode == Activity.RESULT_OK)
        {
            if (data != null)
            {
                val dish = data.getSerializableExtra(MenuActivity.EXTRA_SELECTED_DISH) as? Dish
                if (dish != null)
                {
                    startActivityForResult(EditOrderActivity.intent(this, Order(dish)), REQ_EDIT_ORDER_ACTIVITY)
                }
            }

        }
        else if (requestCode == REQ_EDIT_ORDER_ACTIVITY && resultCode == Activity.RESULT_OK)
        {
            //TODO("Quitar todo los logs")
            Log.v("MY_TAG", "Table Actuvity. De vuelta de editar")

            if (data != null)
            {
                val order = data.getSerializableExtra(EditOrderActivity.RETURNED_ORDER) as? Order
                if (order != null)
                {
                    Log.v("MY_TAG", "Table activity. Añadiento pedido a la mesa ${order}")

                    table_.orders.add(order)
                    adapter.notifyDataSetChanged()
                    invalidateOptionsMenu()
                    setResult(Activity.RESULT_OK)
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_table, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean
    {
        menu?.findItem(R.id.menu_calculate_bill)?.setEnabled(Tables[position_].orders.size != 0)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if (item?.itemId == R.id.menu_calculate_bill)
        {
            val total = Tables[position_].orders.map { order -> order.dish.price }.sum()

            AlertDialog.Builder(this)
                    .setTitle("Cuenta de la mesa ${position_}")
                    .setMessage("El total de la cuenta es ${total}€")
                    .setPositiveButton(getString(R.string.menu_calcular_cuenta_ready), { dialog, _ ->
                        dialog.dismiss()
                    })
                    .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setAddDishButton()
    {
        val addDishButton  = findViewById<View>(R.id.add_dish_button)
        addDishButton.setOnClickListener { view ->
            startActivityForResult(MenuActivity.intent(this), REQ_MENU_ACTIVITY)
        }
    }

    private fun setOrdersList()
    {
        val list = findViewById<ListView>(R.id.ft_orders_list)
        adapter = ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, Tables[position_].orders)
        list.adapter = adapter

        // Nos enteramos de que se ha pulsado un elemento de la lista así:
        //list.setOnItemClickListener { parent, view, position, id ->
        //    // Aviso al listener
        //    onCitySelectedListener?.onCitySelected(Cities.get(position), position)
        //}
    }
}
