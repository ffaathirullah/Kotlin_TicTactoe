package org.d3ifcool.tic_ta_toe.playerVPlayerOnline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_player_v_playeronline.*
import org.d3ifcool.tic_ta_toe.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PlayerVPlayeronline : AppCompatActivity() {
    private val mAuth by lazy { FirebaseAuth.getInstance()}
    private var mFirebaseAnalytics: FirebaseAnalytics?=null

    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference

    var myEmail:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_v_playeronline)
        mFirebaseAnalytics= FirebaseAnalytics.getInstance(this)
        var currentUser = mAuth.currentUser
        myEmail=currentUser?.email
        IncommingCalls()
    }

     fun buRequestEvent(view:View){
        var userDemail=etEmail.text.toString()
        myRef.child("Users").child(SplitString(userDemail)).child("Request").push().setValue(myEmail)
        playerOnline(SplitString(myEmail!!)+ SplitString(userDemail))
        playerOnline1(SplitString(myEmail!!)+ SplitString(userDemail))
        PlayerSymbol="X"
         btn_restart_online.visibility = View.VISIBLE
         restart(btn1_online, btn2_online, btn3_online, btn4_online, btn5_online, btn6_online, btn7_online, btn8_online, btn9_online)
    }

    fun buAcceptEvent(view:android.view.View){
        var userDemail=etEmail.text.toString()
            myRef.child("Users").child(SplitString(userDemail)).child("Request").push()
                .setValue(myEmail)

        playerOnline(SplitString(userDemail)+SplitString(myEmail!!))
        playerOnline1(SplitString(userDemail)+SplitString(myEmail!!))
        PlayerSymbol="O"
        btn_restart_online.visibility = View.VISIBLE
        restart(btn1_online, btn2_online, btn3_online, btn4_online, btn5_online, btn6_online, btn7_online, btn8_online, btn9_online)

    }
    fun btnklik_online(view: View){
        view as Button

        val btnPilih : Button = view
        var btnId = 0

        when(btnPilih.id){
            R.id.btn1_online -> btnId = 1
            R.id.btn2_online -> btnId = 2
            R.id.btn3_online -> btnId = 3
            R.id.btn4_online -> btnId = 4
            R.id.btn5_online -> btnId = 5
            R.id.btn6_online -> btnId = 6
            R.id.btn7_online -> btnId = 7
            R.id.btn8_online -> btnId = 8
            R.id.btn9_online -> btnId = 9
        }
        myRef.child("PlayerOnline").child(sessionId!!).child(btnId.toString()).setValue(myEmail)
        myRef.child("PlayerOnline").removeValue()
        myRef.child("PlayerOnline1").child(sessionId!!).child(btnId.toString()).setValue(myEmail)
        cekPemenang()
    }

    var aktifPlayer = 1
    var pemain1 = ArrayList<Int>()
    var pemain2 = ArrayList<Int>()


    fun playTheGame(btnId: Int, btnPilih: Button){
        if (aktifPlayer == 1){
            txt_result_online.setTextColor(resources.getColor(R.color.White))
            txt_result_online.text = "Player 2 Move"
            btnPilih.setBackgroundResource(R.drawable.alpha_s1)
            pemain1.add(btnId)
            aktifPlayer = 2
            btnPilih.isEnabled = false
        }else{
            txt_result_online.setTextColor(resources.getColor(R.color.white))
            txt_result_online.text = "Player 1 Move"
            btnPilih.setBackgroundResource(R.drawable.alpha_o)
            pemain2.add(btnId)
            aktifPlayer = 1
            btnPilih.isEnabled = false
        }
        cekPemenang()
    }

    var pemenang = 0



    fun restart(btnPilih: Button,
                btnPilih1: Button,
                btnPilih2: Button,
                btnPilih3: Button,
                btnPilih4: Button,
                btnPilih5: Button,
                btnPilih6: Button,
                btnPilih7: Button,
                btnPilih8: Button
    ){
        btn_restart_online.setOnClickListener {
            myRef.child("PlayerOnline").removeValue()
            myRef.child("PlayerOnline1").removeValue()
            myRef.child("Users").removeValue()
            if (aktifPlayer ==1){
                txt_result_online.setTextColor(resources.getColor(R.color.white))
                txt_result_online.text = "Player 1 Move"
            }else if (aktifPlayer ==2){
                txt_result_online.setTextColor(resources.getColor(R.color.White))
                txt_result_online.text = "Player 2 Move"
            }

            btn_restart_online.visibility = View.INVISIBLE
            pemenang =  0
            pemain1.clear()
            pemain2.clear()

            btnPilih.setBackgroundResource(R.drawable.kotak)
            btnPilih.text = " "
            btnPilih.isEnabled = true

            btnPilih1.setBackgroundResource(R.drawable.kotak)
            btnPilih1.text = " "
            btnPilih1.isEnabled = true

            btnPilih2.setBackgroundResource(R.drawable.kotak)
            btnPilih2.text = " "
            btnPilih2.isEnabled = true

            btnPilih3.setBackgroundResource(R.drawable.kotak)
            btnPilih3.text = " "
            btnPilih3.isEnabled = true


            btnPilih4.setBackgroundResource(R.drawable.kotak)
            btnPilih4.text = " "
            btnPilih4.isEnabled = true

            btnPilih5.setBackgroundResource(R.drawable.kotak)
            btnPilih5.text = " "
            btnPilih5.isEnabled = true

            btnPilih6.setBackgroundResource(R.drawable.kotak)
            btnPilih6.text = " "
            btnPilih6.isEnabled = true

            btnPilih7.setBackgroundResource(R.drawable.kotak)
            btnPilih7.text = " "
            btnPilih7.isEnabled = true

            btnPilih8.setBackgroundResource(R.drawable.kotak)
            btnPilih8.text = " "
            btnPilih8.isEnabled = true
        }
    }
    fun  SplitString(str:String):String{
        var split=str.split("@")
        return split[0]
    }

    var number=0
    fun IncommingCalls(){
        myRef.child("Users").child(SplitString(myEmail!!)).child("Request")
            .addValueEventListener(object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    try{
                        val td= dataSnapshot.value as HashMap<String,Any>
                        if(td!=null){

                            var value:String
                            for (key in td.keys){
                                value= td[key] as String
                                etEmail.setText(value)

                                val notifyme=Notifications()
                                notifyme.Notify(applicationContext,value + " want to play tic tac toy",number)
                                number++
                                myRef.child("Users").child(SplitString(myEmail!!)).child("Request").setValue(true)

                                break

                            }

                        }

                    }catch (ex:Exception){}
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            })
    }
    var sessionId:String?=null
    var PlayerSymbol:String?=null
    fun playerOnline(sessionId1:String){
        cekPemenang()
        this.sessionId = sessionId1
        myRef.child("PlayerOnline").removeValue()
        myRef.child("PlayerOnline").child(sessionId!!).addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                try {

                }catch (ex:java.lang.Exception){
                }
            }
            override fun onDataChange(p0: DataSnapshot) {
                try {
                    var tabledata =p0.getValue(object: GenericTypeIndicator<HashMap<String, Any>>() { })

                    if (tabledata!=null){
                        var value:String
                        for (key in tabledata.keys){
                            //read table data store it in value
                            value = tabledata[key] as String

                            if (value != myEmail){
                                aktifPlayer = if (PlayerSymbol == "X") 1 else 2
                            }else{
                                aktifPlayer = if (PlayerSymbol == "X") 2 else 1
                            }
                            AutoPlay(key.toInt())
                        }
                    }
                }catch (ex:java.lang.Exception){
                }
            }
        })
    }

    fun playerOnline1(sessionId1:String){
        this.sessionId = sessionId1
        myRef.child("PlayerOnline1").removeValue()
        myRef.child("PlayerOnline1").child(sessionId!!).addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                try {

                }catch (ex:java.lang.Exception){
                }
            }
            override fun onDataChange(p0: DataSnapshot) {
                try {
                    var tabledata =p0.getValue(object: GenericTypeIndicator<HashMap<String, Any>>() { })

                    if (tabledata!=null){
                        var value:String
                        for (key in tabledata.keys){
                            //read table data store it in value
                            value = tabledata[key] as String

                            if (value != myEmail){
                                aktifPlayer = if (PlayerSymbol == "X") 1 else 2
                            }else{
                                aktifPlayer = if (PlayerSymbol == "X") 2 else 1
                            }
                            AutoPlay(key.toInt())
                        }
                    }
                }catch (ex:java.lang.Exception){
                }
            }
        })
    }


    fun AutoPlay(cellID: Int){

        cellID

        var buSelect:Button? = when(cellID){
            1-> btn1_online
            2-> btn2_online
            3-> btn3_online
            4-> btn4_online
            5-> btn5_online
            6-> btn6_online
            7-> btn7_online
            8-> btn8_online
            9-> btn9_online
            else->{
                btn9_online
            }
        }

        if (buSelect != null) {

            playTheGame(cellID,buSelect)
        }

    }

    fun cekPemenang(){

        //untuk baris 1
        if (pemain1.contains(1) && pemain2.contains(2) && pemain1.contains(3)){
            pemenang = 1
        }
        if (pemain2.contains(1) && pemain1.contains(2) && pemain2.contains(3)){
            pemenang = 2
        }

        //untuk baris 2
        if (pemain1.contains(4) && pemain2.contains(5) && pemain1.contains(6)){
            pemenang = 1
        }
        if (pemain2.contains(4) && pemain1.contains(5) && pemain2.contains(6)){
            pemenang = 2
        }

        //untuk baris 3
        if (pemain1.contains(7) && pemain2.contains(8) && pemain1.contains(9)){
            pemenang = 1
        }
        if (pemain2.contains(7) && pemain1.contains(8) && pemain2.contains(9)){
            pemenang = 2
        }

        //untuk kolom 1
        if (pemain1.contains(1) && pemain2.contains(4) && pemain1.contains(7)){
            pemenang = 1
        }
        if (pemain2.contains(1) && pemain1.contains(4) && pemain2.contains(7)){
            pemenang = 2
        }
        //untuk kolom 2
        if (pemain1.contains(2) && pemain2.contains(5) && pemain1.contains(8)){
            pemenang = 1
        }
        if (pemain2.contains(2) && pemain1.contains(5) && pemain2.contains(8)){
            pemenang = 2
        }
        //untuk kolom 3
        if (pemain1.contains(3) && pemain2.contains(6) && pemain1.contains(9)){
            pemenang = 1
        }
        if (pemain2.contains(3) && pemain1.contains(6) && pemain2.contains(9)){
            pemenang = 2
        }

        //untuk kolom/baris miring
        if (pemain1.contains(1) && pemain2.contains(5) && pemain1.contains(9)){
            pemenang = 1
        }

        if (pemain2.contains(1) && pemain1.contains(5) && pemain2.contains(9)){
            pemenang = 2
        }

        //untuk kolom/baris miring 2
        if (pemain1.contains(3) && pemain2.contains(5) && pemain1.contains(7)){
            pemenang = 1
        }

        if (pemain2.contains(3) && pemain1.contains(5) && pemain2.contains(7)){
            pemenang = 2
        }


        if (pemenang == 1){
            txt_result_online.setTextColor(resources.getColor(R.color.biru))
            txt_result_online.text = "Player 1 Win!!!!"
            txt_result_online.visibility = View.VISIBLE
            btn_restart_online.visibility = View.VISIBLE
            restart(btn1_online, btn2_online, btn3_online, btn4_online, btn5_online, btn6_online, btn7_online, btn8_online, btn9_online)
            btn1_online.isEnabled = false
            btn2_online.isEnabled = false
            btn3_online.isEnabled = false
            btn4_online.isEnabled = false
            btn5_online.isEnabled = false
            btn6_online.isEnabled = false
            btn7_online.isEnabled = false
            btn8_online.isEnabled = false
            btn9_online.isEnabled = false
        }else if (pemenang == 2){
            txt_result_online.setTextColor(resources.getColor(R.color.biru))
            txt_result_online.text = "Player 2 Win!!!"
            txt_result_online.visibility = View.VISIBLE
            btn_restart_online.visibility = View.VISIBLE
            restart(btn1_online, btn2_online, btn3_online, btn4_online, btn5_online, btn6_online, btn7_online, btn8_online, btn9_online)
            btn1_online.isEnabled = false
            btn2_online.isEnabled = false
            btn3_online.isEnabled = false
            btn4_online.isEnabled = false
            btn5_online.isEnabled = false
            btn6_online.isEnabled = false
            btn7_online.isEnabled = false
            btn8_online.isEnabled = false
            btn9_online.isEnabled = false
        }
    }
}