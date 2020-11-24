package com.reazon.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.reazon.foodrunner.R

class LoginActivity : AppCompatActivity() {
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText
    lateinit var btnLogin:Button
    lateinit var txtForgotPassword:TextView
    lateinit var txtSignUp:TextView

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtSignUp = findViewById(R.id.txtSignUp)

        mAuth =FirebaseAuth.getInstance()


        btnLogin.setOnClickListener {
            loginUser()

        }

        txtForgotPassword.setOnClickListener {
            forgotPassword()
        }

        txtSignUp.setOnClickListener {
            openActivityRegister()

            }
    }

    fun openActivityRegister(){
        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun forgotPassword(){
        val intent = Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginUser(){
        val email:String =etEmail.text.toString()
        val password:String = etPassword.text.toString()

        when {
            email == "" -> {
                Toast.makeText(this@LoginActivity, "please write email.", Toast.LENGTH_LONG).show()
            }
            password == "" -> {
                Toast.makeText(this@LoginActivity, "please write password.", Toast.LENGTH_LONG).show()
            }
            else -> {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                        {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("Email",email)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity, "Error Message: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }
}