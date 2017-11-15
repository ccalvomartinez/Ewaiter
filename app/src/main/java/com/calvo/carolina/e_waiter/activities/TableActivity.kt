package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.fragments.TablesListFragment
import com.calvo.carolina.e_waiter.models.Dish
import com.calvo.carolina.e_waiter.models.Order
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.models.Tables
import com.calvo.carolina.e_waiter.utils.loadFragment
import kotlinx.android.synthetic.main.activity_table.*

class TableActivity : AppCompatActivity(), TablesListFragment.OnTableSelectedListener
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

    private var _position: Int = 0
        set(value)
        {
            field = value
            _table = Tables[value]
        }
    private lateinit var _table: Table
    private lateinit var adapter: ArrayAdapter<Order>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        initializeLateinitVariables()
        loadFragment(this, R.id.at_table_list_fragment, TablesListFragment.newInstance())
        setActionBar()
        setAddDishButton()
        setOrdersList()
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
            if (data != null)
            {
                val order = data.getSerializableExtra(EditOrderActivity.RETURNED_ORDER) as? Order
                if (order != null)
                {
                    _table.orders.add(order)
                    // Avisamos al adapter de que los datos han cambiado
                    adapter.notifyDataSetChanged()
                    // Repintamos el menú, por si hay que habilitar el botón
                    invalidateOptionsMenu()
                    // Avisamos a la TableListActivity de que hay un nuevo pedido para que actualice el fragment (TableListFragment)
                    setResult(Activity.RESULT_OK)
                    // En caso de que esta actividad contenga el fragmento TableListActivity, también lo actualizamos
                    val tablesListFragment = fragmentManager.findFragmentById(R.id.at_table_list_fragment) as? TablesListFragment
                    tablesListFragment?.onDataChanged()
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
        menu?.findItem(R.id.menu_calculate_bill)?.setEnabled(Tables[_position].orders.size != 0)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if (item?.itemId == R.id.menu_calculate_bill)
        {
            val total = Tables[_position].orders.map { order -> order.dish.price }.sum()

            AlertDialog.Builder(this)
                    .setTitle("Cuenta de la mesa ${_position}")
                    .setMessage("El total de la cuenta es ${total}€")
                    .setPositiveButton(getString(R.string.menu_calcular_cuenta_ready), { dialog, _ ->
                        dialog.dismiss()
                    })
                    .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTableSelected(position: Int)
    {
        _position = position
        updateActionBarTitle()
        setOrdersList()
    }

    private fun initializeLateinitVariables()
    {
        _position = intent.getIntExtra(EXTRA_TABLE_POSITION, 0)
    }
    private fun setActionBar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        updateActionBarTitle()
    }

    private fun updateActionBarTitle()
    {
        supportActionBar?.title = _table.name
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
        val list = findViewById<ListView>(R.id.at_orders_list)
        adapter = ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, Tables[_position].orders)
        list.adapter = adapter

        // Nos enteramos de que se ha pulsado un elemento de la lista así:
        //list.setOnItemClickListener { parent, view, position, id ->
        //    // Aviso al listener
        //    onCitySelectedListener?.onCitySelected(Cities.get(position), position)
        //}
    }
}
