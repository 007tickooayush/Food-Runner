package com.reazon.foodrunner.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.reazon.foodrunner.R
import kotlin.system.exitProcess


class LogOutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_out, container, false)

        val mAuth = FirebaseAuth.getInstance()

        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle("Log Out")
            .setMessage("Are you sure ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                mAuth.signOut()//Toast.makeText(context,"Yes",Toast.LENGTH_SHORT).show()
                if (mAuth.currentUser == null) {
                    exitProcess(id)
                }
            }
            .setNegativeButton("No") { dialog, id ->
                openHome()
                dialog.cancel()
            }


        val alertDialog = dialogBuilder.create()

        alertDialog.show()

        return view
    }

    fun openHome() {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frame, HomeFragment())
        transaction.commit()
    }

}