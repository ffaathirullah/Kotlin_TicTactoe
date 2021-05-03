package org.d3ifcool.tic_ta_toe.authentikasi

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.tic_ta_toe.main.MainActivity
import org.d3ifcool.tic_ta_toe.R


class Login : AppCompatActivity() {

    private val mAuth by lazy { FirebaseAuth.getInstance()}
    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference

    private val dialogBuilder by lazy { AlertDialog.Builder(this)}
    private lateinit var dialogLoading: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        initLoadingDialog()

        val emailTxt = findViewById<View>(R.id.et_username1) as EditText
        val passwordTxt = findViewById<View>(R.id.et_password1) as EditText


        val btnRegister: Button = findViewById(R.id.btn_register)
        btnRegister.setOnClickListener {
            val goRegister = Intent(this, Register::class.java)
            startActivity(goRegister)
            finish()
        }


        val fabLogin: FloatingActionButton = findViewById(R.id.fab_login)
        fabLogin.setOnClickListener{
            var email = emailTxt.text.toString()
            var password = passwordTxt.text.toString()

            if (!email.isEmpty() && !password.isEmpty() ) {
                showLoading()
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            hideLoading()
                            var currentUser = mAuth.currentUser
                            //save in database
                            if(currentUser!=null) {
                                myRef.child("Users").child(SplitString(currentUser.email.toString())).child("Request").setValue(currentUser.uid)
                            }

                            LoadMain()
                        } else {
                            hideLoading()
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }else {
                Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
            }
        }
    }

    private  fun initLoadingDialog(){
        dialogBuilder.setView(R.layout.dialog_loading)
        dialogLoading = dialogBuilder.create()
        dialogLoading.setCancelable(false)
    }

    private  fun showLoading(){
        dialogLoading.show()
    }

    private  fun hideLoading(){
        dialogLoading.dismiss()
    }

    fun isLogin(): Boolean {
        val session = mAuth.currentUser
        return session != null
    }

    private fun checkSessionUser(){
        if (isLogin()){
            Intent(this,
                MainActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LoadMain()
        checkSessionUser()
    }

    fun  LoadMain(){
        var currentUser = mAuth.currentUser

        if(currentUser!=null) {


            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)
            finish()
        }
    }

    fun  SplitString(str:String):String{
        var split=str.split("@")
        return split[0]
    }
}