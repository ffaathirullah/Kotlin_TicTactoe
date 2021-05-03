package org.d3ifcool.tic_ta_toe.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.tic_ta_toe.authentikasi.Login
import org.d3ifcool.tic_ta_toe.chat.ChatActivity
import org.d3ifcool.tic_ta_toe.playerVComp.PlayerWComp
import org.d3ifcool.tic_ta_toe.playerVPlayer.PlayerWPlayer
import org.d3ifcool.tic_ta_toe.R
import org.d3ifcool.tic_ta_toe.playerVPlayerOnline.PlayerVPlayeronline


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mAuth by lazy { FirebaseAuth.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_move)
        btnMoveActivity.setOnClickListener(this)

        val btnMoveActivity2: Button = findViewById(R.id.btn_move_to_comp)
        btnMoveActivity2.setOnClickListener(this)

        val btnMoveActivity3: Button = findViewById(R.id.btn_logout)
        btnMoveActivity3.setOnClickListener(this)

        val btnMoveActivity4: Button = findViewById(R.id.btn_to_group)
        btnMoveActivity4.setOnClickListener(this)

        val btnMoveActivity5: Button = findViewById(R.id.btn_move_to_online)
        btnMoveActivity5.setOnClickListener(this)

    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_move_to_online -> {
                val moveIntent4 = Intent(this@MainActivity, PlayerVPlayeronline::class.java)
                startActivity(moveIntent4)
            }
            R.id.btn_move -> {
                val moveIntent = Intent(this@MainActivity, PlayerWPlayer::class.java)
                startActivity(moveIntent)
            }
            R.id.btn_move_to_comp -> {
                val moveIntent1 = Intent(this@MainActivity, PlayerWComp::class.java)
                startActivity(moveIntent1)
            }
            R.id.btn_logout -> {
                 mAuth.signOut()
                val moveIntent2 = Intent(this@MainActivity, Login::class.java)
                startActivity(moveIntent2)
                finish()
             }
            R.id.btn_to_group -> {
            val moveIntent3 = Intent(this@MainActivity, ChatActivity::class.java)
            startActivity(moveIntent3)
        }
            }
        }

}

