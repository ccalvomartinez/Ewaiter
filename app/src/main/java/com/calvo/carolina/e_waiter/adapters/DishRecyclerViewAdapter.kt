package com.calvo.carolina.e_waiter.adapters

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.calvo.carolina.e_waiter.R
import com.calvo.carolina.e_waiter.models.Dish
import com.calvo.carolina.e_waiter.models.MenuLetter
import kotlinx.android.synthetic.main.content_menu_dish_item.view.*

class DishRecyclerViewAdapter: RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishViewHolder
    {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_menu_dish_item, parent, false)
        if (Build.VERSION.SDK_INT >= 21)
        {
            view.elevation = 4f
        }
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder?, position: Int)
    {
        holder?.bindDish(MenuLetter.dishes[position])
    }

    override fun getItemCount(): Int
    {
       return MenuLetter.dishes.count()
    }

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
         fun bindDish(dish: Dish)
         {
             itemView.cmd_dish_name_text.text = dish.name
             itemView.cmd_dish_price_text.text = dish.price.toString()
             for (i in 0 until dish.allergens.size)
             {
                 when( dish.allergens[i])
                 {
                     "a01" -> itemView.cmd_gluten_01.visibility = View.VISIBLE
                     "a02" -> itemView.cmd_egg_02.visibility = View.VISIBLE
                     "a03" -> itemView.cmd_fish_03.visibility = View.VISIBLE
                     "a04" -> itemView.cmd_milk_04.visibility = View.VISIBLE
                     "a05" -> itemView.cmd_nuts_05.visibility = View.VISIBLE
                 }
             }
         }
    }
}