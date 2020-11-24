package com.reazon.foodrunner.adapter

import android.content.Context
import android.os.Bundle
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
import com.google.firebase.database.FirebaseDatabase
import com.reazon.foodrunner.R
import com.reazon.foodrunner.model.Restaurant
import com.squareup.picasso.Picasso


class HomeRecyclerAdapter(
    var context: Context,
    var restaurantList: List<Restaurant>,
    private val listener:ContentListener
) : RecyclerView.Adapter<HomeRecyclerAdapter.HomeRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_single_row, parent, false)
        return HomeRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        val hashMap = HashMap<String, Any>()

        holder.costForOne.text = restaurant.cost_for_one.toString()
        holder.rating.text = restaurant.rating.toString()
        holder.textView.text = restaurant.name.toString()
        Picasso.get().load(restaurant.image_url.toString()).placeholder(R.drawable.food_runner_icon)
            .into(holder.imageView)

        holder.btnFavourite.setOnClickListener {
            Toast.makeText(context, "Updated values", Toast.LENGTH_SHORT).show()

            val firebaseUid = FirebaseAuth.getInstance().currentUser!!.uid
            val mDatabaseReference =
                FirebaseDatabase.getInstance().reference.child("Favourites").child(firebaseUid)

            Toast.makeText(
                context,
                " ${holder.textView.text} added toFavourites",
                Toast.LENGTH_SHORT
            ).show()

            hashMap["name"] = restaurant.name.toString()
            hashMap["rating"] = restaurant.rating.toString()
            hashMap["id"] = restaurant.id.toString()
            hashMap["cost_for_one"] = restaurant.cost_for_one.toString()
            hashMap["image_url"] = restaurant.image_url.toString()

            mDatabaseReference.updateChildren(hashMap)
            // !event listener
        }

        holder.bind(holder.textView.text.toString(),restaurantList as ArrayList<Restaurant>,listener)
    }


    class HomeRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(name:String,restaurant: ArrayList<Restaurant>, listener: ContentListener) {
            itemView.setOnClickListener{
                listener.onItemClicked(restaurant[position])
//                val bundle = Bundle()
//                bundle.putString("restaurantName",name)
            }
        }

        var imageView: ImageView = view.findViewById(R.id.imgRestaurantImage)
        var costForOne: TextView = view.findViewById(R.id.txtCostForOne)
        var textView: TextView = view.findViewById(R.id.txtRestaurantName)
        var btnFavourite: ImageButton = view.findViewById(R.id.btnFavourite)
        var rating: TextView = view.findViewById(R.id.txtRating)
    }

    interface ContentListener {
        fun onItemClicked(item: Restaurant)
    }
}
