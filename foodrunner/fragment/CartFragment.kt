package com.reazon.foodrunner.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.activity.FinishActivity
import com.reazon.foodrunner.model.Order
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.recycler_cart_single_row.view.*

class CartFragment : Fragment() {

    private lateinit var btnCheckout: Button
//    private var orderList: MutableList<Order> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        btnCheckout = view.findViewById(R.id.btnCheckout)


        retrieveOrders()
        btnCheckout.setOnClickListener {
            startActivity(Intent(activity, FinishActivity::class.java))
        }

        return view
    }

    private fun retrieveOrders() {
        val user = FirebaseAuth.getInstance().currentUser!!.uid

        var index = 0
        val i = index.toString()
        val time = arguments?.getString("time_order").toString()
        Log.d("TLRO", time)
        val ref = FirebaseDatabase.getInstance().getReference("/Orders/$user/$time/")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                recyclerCart.adapter = adapter
                snapshot.children.forEach {
                    val order = it.getValue(Order::class.java)
                    Log.d("TLC", it.toString())
                    adapter.add(OrderItem(order!!))
                    index++
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TLE", error.message)
            }

        })
    }

    class OrderItem(private val order: Order) : Item<GroupieViewHolder>() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.txtItemNameCart.text = order.item
            viewHolder.itemView.txtItemCostCart.text = order.cost.toString()
        }

        override fun getLayout(): Int {
            return R.layout.recycler_cart_single_row
        }

    }

}