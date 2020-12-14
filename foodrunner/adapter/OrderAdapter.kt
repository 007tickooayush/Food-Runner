package com.reazon.foodrunner.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reazon.foodrunner.R
import com.reazon.foodrunner.model.Order

class OrderAdapter(
    var context: Context,
    var ordersList: MutableList<Order>
):RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        //items
        itemCount
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_order_history_single_row,parent,false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = ordersList[position]

        holder.orderName.text = order.item.toString()
        holder.orderCost.text = order.cost.toString()

    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    class OrderViewHolder(view: View):RecyclerView.ViewHolder(view){
        var orderName:TextView = view.findViewById(R.id.txtOrderItemName)
        var orderCost:TextView = view.findViewById(R.id.txtOrderItemCost)
    }
}