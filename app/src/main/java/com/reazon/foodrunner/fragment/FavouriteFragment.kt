package com.reazon.foodrunner.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.adapter.FavouriteRecyclerAdapter
import com.reazon.foodrunner.model.Favourite

class FavouriteFragment : Fragment() {

    private var recyclerAdapter: FavouriteRecyclerAdapter? = null
    private var recyclerFavourite: RecyclerView? = null
    internal var favouritesList: MutableList<Favourite> = ArrayList()
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        //one user can add only up to one favourite restaurants
        recyclerFavourite = view.findViewById(R.id.recyclerFavouriteAll)
        layoutManager = LinearLayoutManager(activity)
        recyclerFavourite!!.layoutManager = layoutManager
        retrieveAllFavouriteRestaurants()

        return view
    }

    private fun retrieveAllFavouriteRestaurants() {
        favouritesList.clear()
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val mDatabaseReference =
            FirebaseDatabase.getInstance().reference.child("Favourites/$firebaseUser")//.child()
        mDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR ", "" + error.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val favorite = p0.getValue(Favourite::class.java)
                recyclerAdapter = FavouriteRecyclerAdapter(context!!, favouritesList)
                recyclerFavourite?.adapter = recyclerAdapter

                if (favorite != null) {
                    favouritesList.add(favorite)
                }

            }
        })

    }

}