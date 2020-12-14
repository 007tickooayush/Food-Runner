package com.reazon.foodrunner.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.adapter.HomeRecyclerAdapter
import com.reazon.foodrunner.model.Restaurant

class HomeFragment : Fragment(),HomeRecyclerAdapter.ContentListener{

//    private var favDatabaseReference: DatabaseReference? = null
    private var recyclerAdapter: HomeRecyclerAdapter? = null
    private var recyclerHome: RecyclerView? = null
    internal var restaurantList: MutableList<Restaurant> = ArrayList()
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerHome = view.findViewById(R.id.recyclerHomeAll)


        layoutManager = LinearLayoutManager(activity)
        recyclerHome!!.layoutManager = layoutManager

        retrieveAllRestaurants()

        return view
    }

    private fun retrieveAllRestaurants() {
        restaurantList.clear()
        val restaurantReference = FirebaseDatabase.getInstance().reference.child("Restaurants")

        restaurantReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR ", "" + error.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val restaurant = snapshot.getValue(Restaurant::class.java)

                    if (restaurant != null) {
                        restaurantList.add(restaurant)
                    }
                    recyclerAdapter = HomeRecyclerAdapter(context!! , restaurantList,this@HomeFragment)
                    recyclerHome?.adapter = recyclerAdapter
                }
            }

        })

    }

    override fun onItemClicked(item: Restaurant) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frame,MenuFragment())//?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

}