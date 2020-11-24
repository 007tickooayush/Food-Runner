package com.reazon.foodrunner.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.adapter.MenuRecyclerAdapter
import com.reazon.foodrunner.model.MenuItem
import com.reazon.foodrunner.model.Restaurant
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.recycler_home_single_row.*
import kotlinx.android.synthetic.main.recycler_menu_single_row.*

class MenuFragment : Fragment(), MenuRecyclerAdapter.ContentListener {

    private var menuRecyclerAdapter: MenuRecyclerAdapter? = null
    private lateinit var menuReference: DatabaseReference
    private var recyclerMenu: RecyclerView? = null
    internal var menuItemList: MutableList<MenuItem> = ArrayList()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var proceedToCartLayout: RelativeLayout
    private lateinit var btnProceedToCart: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        menuReference = FirebaseDatabase.getInstance().reference.child("Menu")
        recyclerMenu = view.findViewById(R.id.recyclerMenu)
        btnProceedToCart = view.findViewById(R.id.btnGoToCart)
        proceedToCartLayout = view.findViewById(R.id.rlProceedToCart)

        layoutManager = LinearLayoutManager(activity)
        recyclerMenu!!.layoutManager = layoutManager

        retrieveMenu()

        return view
    }

    private fun retrieveMenu() {
        menuItemList.clear()
        menuReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", "" + error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (p0 in snapshot.children) {
                    val menu = p0.getValue(MenuItem::class.java)

                    menuRecyclerAdapter = MenuRecyclerAdapter(context!!,
                        btnProceedToCart,
                        proceedToCartLayout,
                        menuItemList,
                        this@MenuFragment)//,this@MenuFragment)
                    recyclerMenu!!.adapter = menuRecyclerAdapter


                    if (menu != null) {
                        menuItemList.add(menu)
                    }

                }
            }

        })
    }

    fun onBackPressed() {
        when (this.fragmentManager?.findFragmentById(R.id.frame)) {
            !is HomeFragment -> openHome()
        }

    }

    private fun openHome() {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, HomeFragment())
        transaction?.commit()
    }

    override fun openCart() {
        val localTime = java.util.Calendar.getInstance().time
        val regex = Regex("[^A-Za-z0-9]")
        val time = regex.replace(localTime.toString(), " ")

        val bundle = Bundle()
        bundle.putString("time_order",time)
        val fragNext = CartFragment()
        fragNext.arguments = bundle
        Log.d("TLMF",time)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, CartFragment())
        transaction?.commit()
    }
}

