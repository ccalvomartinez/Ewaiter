package com.calvo.carolina.e_waiter.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.fragments.TablesListFragment
import com.calvo.carolina.e_waiter.models.Dish
import com.calvo.carolina.e_waiter.models.MenuLetter
import com.calvo.carolina.e_waiter.models.Table
import com.calvo.carolina.e_waiter.utils.*
import kotlinx.android.synthetic.main.activity_table_list.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONObject
import java.net.URL
import java.util.*

class TableListActivity : AppCompatActivity(), TablesListFragment.OnTableSelectedListener
{
    companion object
    {
        val REQ_TABLE = 231
    }
    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        TABLES(1)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        loadFragment(this,R.id.atl_fragment_table_list,TablesListFragment.newInstance())
        setViewSwitcher()
        //TODO("Encontrar otra forma de comprobar que se ha cargado el menu")
        if (MenuLetter.dishes.size == 0)
        {
            loadMenu()
        }

    }

    override fun onTableSelected(table: Table, position: Int)
    {
        startActivityForResult(TableActivity.intent(this, position), REQ_TABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_TABLE && resultCode == Activity.RESULT_OK)
        {
            val tablesListFragment = fragmentManager.findFragmentById(R.id.atl_fragment_table_list) as? TablesListFragment
            tablesListFragment?.onDataChanged()
        }
    }
    private  fun setViewSwitcher()
    {
        view_switcher.setInAnimation(this, android.R.anim.fade_in)
        view_switcher.setOutAnimation(this, android.R.anim.fade_out)
    }

    private  fun loadMenu()
    {
        view_switcher.displayedChild = VIEW_INDEX.LOADING.index
        val activity = this
        async(UI) {
            val newDishesDownloader: Deferred<List<Dish>?> = bg {
                downloadDishes()
            }

            val downloadedDishes = newDishesDownloader.await()
            view_switcher.displayedChild = VIEW_INDEX.TABLES.index
            if (downloadedDishes != null) {
                val r = MenuLetter.dishes.addAll(0, downloadedDishes)
            }
            else {
                // Ha habido algún tipo de error, se lo decimos al usuario con un diálogo
                AlertDialog.Builder(activity)
                        .setTitle("Error")
                        .setMessage("Error while downloading menu. Check your internet connection.")
                        .setPositiveButton("Retry", { dialog, _ ->
                            dialog.dismiss()
                            loadMenu()
                        })
                        .setNegativeButton("Exit", { _, _ -> activity.finish() })
                        .show()
            }
        }
    }

    private fun downloadDishes(): List<Dish>?
    {
        try {
            // Nos descargamos la información machete
            val url = URL(urlMenuLetter)
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

            // Analizamos los datos que nos acabamos de descargar
            val jsonRoot = JSONObject(jsonString)
            val list = jsonRoot.getJSONArray("menu")

            // Nos creamos la lista que vamos a ir rellenando
            val dishes = mutableListOf<Dish>()

            // Recorremos la lista del objeto JSON
            for (dishIndex in 0 until list.length()) {
                val dish = list.getJSONObject(dishIndex)
                val name = dish.getString("name")
                val imageString = dish.getString("image")
                val dishDescription = dish.getString("description")
                val haveAlergens = dish.getBoolean("hasAllergens")
                val price = dish.getDouble("price").toFloat()
                val alergens = dish.getJSONArray("allergens")

                dishes.add(Dish(name, price, imageString, dishDescription, haveAlergens, allergens = alergens.toListOfArray()))
            }

            return dishes
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }
}
