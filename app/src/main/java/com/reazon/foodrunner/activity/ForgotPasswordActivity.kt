package com.reazon.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.reazon.foodrunner.R


class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var etForgot:EditText
    lateinit var etForgotConfirm:EditText
    lateinit var btnChangePassword:Button
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        etForgot = findViewById(R.id.etForgot)
        etForgotConfirm = findViewById(R.id.etForgotConfirm)
        btnChangePassword = findViewById(R.id.btnChangePassword)

        btnChangePassword.setOnClickListener {
            val email:String = etForgot.text.toString()
            val emailConfirm:String = etForgotConfirm.text.toString()

            if(email.isNotEmpty() && emailConfirm.isNotEmpty()){
                if(email == emailConfirm){
                    // add code to check if the user exists

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener {task->
                        if(task.isSuccessful){
                            Toast.makeText(this@ForgotPasswordActivity,"Check your email to reset your password",Toast.LENGTH_SHORT).show()
                            openLogin()
                        }
                        else{
                            Toast.makeText(this@ForgotPasswordActivity,"Failed to reset your password,check entered email",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@ForgotPasswordActivity,"please enter the required data in the text field",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openLogin(){
        val intent = Intent(this@ForgotPasswordActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onBackPressed() {
        val intent = Intent(this@ForgotPasswordActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
        //super.onBackPressed()
    }
}