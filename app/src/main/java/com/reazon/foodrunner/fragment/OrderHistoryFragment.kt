package com.reazon.foodrunner.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.adapter.OrderAdapter
import com.reazon.foodrunner.model.Order

class OrderHistoryFragment : Fragment() {

//    lateinit var textView: TextView
    private  var recyclerOrder: RecyclerView?= null
    internal var orderAdapter:OrderAdapter? = null
    internal var ordersList:MutableList<Order> = ArrayList()
    private var layoutManager:RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)

//        textView = view.findViewById(R.id.txtOrder)

        recyclerOrder = view.findViewById(R.id.recyclerOrder)
        layoutManager = LinearLayoutManager(activity)
        recyclerOrder!!.layoutManager = layoutManager
        retrieveOrderHistory()

        return view
    }

    private fun retrieveOrderHistory(){
        ordersList.clear()

        val user = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val orderReference= FirebaseDatabase.getInstance().reference.child("Orders").child(user)

        orderReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children){
                    val order = snapshot.getValue(Order::class.java)

                    orderAdapter = OrderAdapter(context!!, ordersList)
                    recyclerOrder?.adapter = orderAdapter

                    if (order != null) {
                        ordersList.add(order)

                        Log.d("OrderAdapter","Order Name: ${order.item}")
                        Log.d("OrderAdapter","Order Cost: ${order.cost}")
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("OrderHistory: ",error.message)
            }

        })

    }
}