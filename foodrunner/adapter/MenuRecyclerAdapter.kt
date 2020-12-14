package com.reazon.foodrunner.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.fragment.CartFragment
//import com.reazon.foodrunner.fragment.Communicator
import com.reazon.foodrunner.model.MenuItem
import kotlin.coroutines.coroutineContext

class MenuRecyclerAdapter(
    val context: Context,
    private val btnProceedToCart: Button,
    private val proceedToCartPassed: RelativeLayout,
    private val menuList: List<MenuItem>,
    private val listener: ContentListener
) : RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder>() {

    private var goToCart: Int = 0
    private var itemSelected: Int = 0
    private var itemNumber: Int = 0
    private var itemSelectedId = arrayListOf<String>()
    private var itemSelectedName = arrayListOf<String>()
    lateinit var proceedToCartLayout: RelativeLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_menu_single_row, parent, false)

        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItemItem = menuList[position]
        holder.menuItemName.text = menuItemItem.item.toString()

        proceedToCartLayout = proceedToCartPassed

        btnProceedToCart.setOnClickListener {
            Toast.makeText(context, "Proceeding To Cart", Toast.LENGTH_SHORT).show()
            listener.openCart()
        }

        holder.btnMenuItem.setOnClickListener {
            if (holder.btnMenuItem.text.toString() == "Remove") {
                itemSelected--
                holder.btnMenuItem.text = "ADD"
                itemSelectedId.remove(holder.btnMenuItem.tag.toString())
                itemSelectedName.remove(holder.menuItemName.text.toString())
                holder.btnMenuItem.setBackgroundResource(R.drawable.btn_menu_add_background)

            } else {
                itemSelected++
                holder.btnMenuItem.text = "Remove"
                itemSelectedId.add(holder.btnMenuItem.tag.toString())
                itemSelectedName.add(holder.menuItemName.text.toString())
                holder.btnMenuItem.setBackgroundResource(R.drawable.btn_menu_remove_background)

            }
            if (itemSelected > 0) {
                proceedToCartLayout.visibility = View.VISIBLE
            } else {
                proceedToCartLayout.visibility = View.INVISIBLE
            }

            print("$goToCart")
            holder.btnMenuItem.tag = menuItemItem.id

            itemNumber = position + 1

            Log.d("TLRI", "" + holder.menuItemName.text)
        }
        holder.btnMenuItem.tag = menuItemItem.id.toString() + ""
        holder.bind(itemSelectedId, itemSelectedName, btnProceedToCart, listener)
    }

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(menuSelectedId: MutableList<String>, menuList: MutableList<String>, btnProceedToCart: Button, listener: ContentListener) {
            btnProceedToCart.setOnClickListener {

                val localTime = java.util.Calendar.getInstance().time
                val regex = Regex("[^A-Za-z0-9]")
                val time = regex.replace(localTime.toString()," ")

                val mReference = FirebaseDatabase.getInstance().reference.child("Orders")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)//.push()
                    .child(time)

                Log.d("TLMR =>",time.toString())
                val orderHashMap = HashMap<String, Any>()

                for (item in menuList.indices) {
                    orderHashMap["id"] = menuSelectedId[item]
                    orderHashMap["item"] = menuList[item]
                    orderHashMap["cost"] = 200
                    orderHashMap["placed"] = true
                    orderHashMap["time"] = time
//                    mReference.updateChildren(orderHashMap)
                    mReference.child(item.toString()).updateChildren(orderHashMap)
                }

//                listener.openCart()
            }
        }

        val menuItemName: TextView = view.findViewById(R.id.menuItemName)
        val btnMenuItem: Button = view.findViewById(R.id.btnMenuAdd)
//        val itemPrice:TextView = view.findViewById(R.id.menuItemCost)
    }

    interface ContentListener {
        fun openCart()
    }
}