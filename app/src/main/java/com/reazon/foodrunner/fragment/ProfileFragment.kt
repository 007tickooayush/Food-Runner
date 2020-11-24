package com.reazon.foodrunner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.reazon.foodrunner.R
import com.reazon.foodrunner.model.User


class ProfileFragment : Fragment() {

    lateinit var txtProfileName: TextView
    lateinit var txtEmail: TextView
    lateinit var txtAddress: TextView
    lateinit var txtPhone:TextView
    lateinit var imgProfile: ImageView
    lateinit var firebaseUser: FirebaseUser

    var user:User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txtProfileName = view.findViewById(R.id.txtProfileName)
        txtEmail = view.findViewById(R.id.txtEmail)
        txtAddress = view.findViewById(R.id.txtAddress)
        txtPhone = view.findViewById(R.id.txtPhoneNumber)
        imgProfile = view.findViewById(R.id.imgProfile)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val reference =
            FirebaseDatabase.getInstance().reference.child("Users")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    for(snapshot in p0.children)
                    {
                        user =snapshot.getValue(User::class.java)
                        txtProfileName.text = user!!.username.toString()
                        txtAddress.text = user!!.address.toString()
                        txtEmail.text = user!!.email.toString()
                        txtPhone.text = user!!.phone.toString()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return view
    }
}