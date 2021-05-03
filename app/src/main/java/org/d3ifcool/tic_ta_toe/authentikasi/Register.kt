package org.d3ifcool.tic_ta_toe.authentikasi

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.tic_ta_toe.R


class Register : AppCompatActivity() {
    private  val mAuth by lazy { FirebaseAuth.getInstance()}

    private val dialogBuilder by lazy { AlertDialog.Builder(this)}
    private lateinit var dialogLoading: Dialog
    private  val databaseReference by lazy { FirebaseDatabase.getInstance().reference}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initLoadingDialog()

        val emailTxt = findViewById<View>(R.id.et_username) as EditText
        val passwordTxt = findViewById<View>(R.id.et_password) as EditText


        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val goLogin = Intent(this, Login::class.java)
            startActivity(goLogin)
            finish()
        }


        val fabRegister: FloatingActionButton = findViewById(R.id.fab_register)
        fabRegister.setOnClickListener{

            showLoading()

            var email = emailTxt.text.toString()
            var password = passwordTxt.text.toString()

            if (!email.isEmpty() && !password.isEmpty() ) {

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            hideLoading()
                            var currentUser = mAuth.currentUser
                            //save in database
                            if(currentUser!=null) {
                                databaseReference.child("Users").child(SplitString(currentUser.email.toString())).child("Request").setValue(currentUser.uid)
                            }
                            startActivity(Intent(this, Login::class.java))
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
    fun  SplitString(str:String):String{
        var split=str.split("@")
        return split[0]
    }
}

