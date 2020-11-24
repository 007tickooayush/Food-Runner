package com.reazon.foodrunner.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.reazon.foodrunner.R
import com.reazon.foodrunner.model.Favourite
import com.reazon.foodrunner.model.Restaurant
import com.squareup.picasso.Picasso
import kotlin.math.log

class FavouriteRecyclerAdapter(
    var context: Context,
    var favouritesList: List<Favourite>
) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row, parent, false)
        return FavouriteRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    override fun onBindViewHolder(holder: FavouriteRecyclerViewHolder, position: Int) {
        val favourite = favouritesList[position]
        //holder.textView.text = text
        holder.costForOne.text = favourite.cost_for_one.toString()
        holder.rating.text = favourite.rating.toString()
        holder.textView.text = favourite.name.toString()
        Picasso.get().load(favourite.image_url.toString()).placeholder(R.drawable.food_runner_icon)
            .into(holder.imageView)
            .toString()

        holder.btnFavourite.setOnClickListener {
            val firebaseUid = FirebaseAuth.getInstance().currentUser!!.uid
            val mDatabaseReference =
                FirebaseDatabase.getInstance().reference.child("Favourites").child(firebaseUid)//.child(favNo)
            //.child(index.toString())

            Toast.makeText(
                context,
                "Favourite ${holder.textView.text} restaurant removed",
                Toast.LENGTH_SHORT
            ).show()

            mDatabaseReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d("ERROR", "" + error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (p0 in snapshot.children) {
                        val hashMap = HashMap<String, Any>()
//                        mDatabaseReference.child("name").setValue(null)
                        hashMap.remove("name")
                        hashMap.remove("rating")
                        hashMap.remove("id")
                        hashMap.remove("cost_for_one")
                        hashMap.remove("image_url")
                        mDatabaseReference.updateChildren(hashMap)
                    }
                }
            })
        }
    }

    class FavouriteRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.imgFavouriteImage)
        var costForOne: TextView = view.findViewById(R.id.txtCostForOneFav)
        var textView: TextView = view.findViewById(R.id.txtFavouriteName)
        var btnFavourite: ImageButton = view.findViewById(R.id.btnFavouriteFav)
        var rating: TextView = view.findViewById(R.id.txtRatingFav)

    }
}