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

class RegisterActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etMobileRegister: EditText
    lateinit var etAddress: EditText
    lateinit var etPasswordRegister: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    private lateinit var firebaseUid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobileRegister = findViewById(R.id.etMobileRegister)
        etAddress = findViewById(R.id.etAddress)
        etPasswordRegister = findViewById(R.id.etPasswordRegister)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        /*val url = "http://13.235.250.119/v2/register/fetch_result"
        val queue =Volley.newRequestQueue(this@RegisterActivity)
        val jsonObjectRequest =object :JsonObjectRequest(Request.Method.POST,url,null, Response.Listener{
            //response handled
        },Response.ErrorListener {
                //Error handled
        }){
        }*/
        mAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            //Register / create user in database
            registerUser()

        }

    }

    private fun registerUser(){
        val email = etEmail.text.toString()
        val password = etPasswordRegister.text.toString()
        val name = etName.text.toString()
        val address = etAddress.text.toString()
        val mobileNumber = etMobileRegister.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && address.isNotEmpty() && mobileNumber.isNotEmpty()){
            if(confirmPasswords()){
                if(password.length>=4 && name.length >= 4){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                        if(task.isSuccessful){
                            firebaseUid =mAuth.currentUser!!.uid
                            mDatabase =FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUid)

                            val userHashMap = HashMap<String,Any>()
                            userHashMap["uid"] = firebaseUid
                            userHashMap["username"] = name
                            userHashMap["address"] = address
                            userHashMap["email"] = email
                            userHashMap["phone"] = mobileNumber

                            mDatabase.updateChildren(userHashMap).addOnCompleteListener {
                                if(task.isSuccessful){
                                    val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                                    Toast.makeText(this,"Registered Successfully",Toast.LENGTH_LONG).show()
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else{
                            Toast.makeText(this@RegisterActivity,"Error could'nt register user Error:"+task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                        }
                    }

                }else{
                    Toast.makeText(this@RegisterActivity,"Length of password and username should be min. 4 characters",Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(this@RegisterActivity,"Entered password doesn't match, please check again.",Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this@RegisterActivity,"Please Enter credentials correctly",Toast.LENGTH_LONG).show()
        }
    }

    fun confirmPasswords():Boolean{
        val entered = etPasswordRegister.text.toString()
        val confirmation = etConfirmPassword.text.toString()
        return (entered == confirmation)
    }
    override fun onBackPressed() {
        val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
        //super.onBackPressed()
    }

}