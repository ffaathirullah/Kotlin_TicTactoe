package org.d3ifcool.tic_ta_toe.playerVPlayer

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game_player.*
import org.d3ifcool.tic_ta_toe.R

class PlayerWPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_player)
    }

    fun btnklik(view: View){
        view as Button

        val btnPilih : Button = view
        var btnId = 0

        when(btnPilih.id){
            R.id.btn1 -> btnId = 1
            R.id.btn2 -> btnId = 2
            R.id.btn3 -> btnId = 3
            R.id.btn4 -> btnId = 4
            R.id.btn5 -> btnId = 5
            R.id.btn6 -> btnId = 6
            R.id.btn7 -> btnId = 7
            R.id.btn8 -> btnId = 8
            R.id.btn9 -> btnId = 9
        }

        playTheGame(btnId, btnPilih)
        cekPemenang()
    }

    var aktifPlayer = 1
    var pemain1 = ArrayList<Int>()
    var pemain2 = ArrayList<Int>()


    fun playTheGame(btnId:Int, btnPilih: Button){
        if (aktifPlayer == 1){
            txt_result.setTextColor(resources.getColor(R.color.White))
            txt_result.text = "Player 2 Move"
            btnPilih.setBackgroundResource(R.drawable.alpha_s1)
            pemain1.add(btnId)
            aktifPlayer = 2
            btnPilih.isEnabled = false
        }else{
            txt_result.setTextColor(resources.getColor(R.color.white))
            txt_result.text = "Player 1 Move"
            btnPilih.setBackgroundResource(R.drawable.alpha_o)
            pemain2.add(btnId)
            aktifPlayer = 1
            btnPilih.isEnabled = false
        }
    }

    var pemenang = 0

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
            txt_result.setTextColor(resources.getColor(R.color.biru))
           txt_result.text = "Player 1 Win!!!!"
            txt_result.visibility = View.VISIBLE
            btn_restart.visibility = View.VISIBLE
            restart(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
            btn1.isEnabled = false
            btn2.isEnabled = false
            btn3.isEnabled = false
            btn4.isEnabled = false
            btn5.isEnabled = false
            btn6.isEnabled = false
            btn7.isEnabled = false
            btn8.isEnabled = false
            btn9.isEnabled = false
        }else if (pemenang == 2){
            txt_result.setTextColor(resources.getColor(R.color.biru))
            txt_result.text = "Player 2 Win!!!"
            txt_result.visibility = View.VISIBLE
            btn_restart.visibility = View.VISIBLE
            restart(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
            btn1.isEnabled = false
            btn2.isEnabled = false
            btn3.isEnabled = false
            btn4.isEnabled = false
            btn5.isEnabled = false
            btn6.isEnabled = false
            btn7.isEnabled = false
            btn8.isEnabled = false
            btn9.isEnabled = false
        }
    }

    fun restart(btnPilih: Button,
                btnPilih1: Button,
                btnPilih2: Button,
                btnPilih3: Button,
                btnPilih4: Button,
                btnPilih5: Button,
                btnPilih6: Button,
                btnPilih7: Button,
                btnPilih8: Button){
        btn_restart.setOnClickListener {
            if (aktifPlayer ==1){
                txt_result.setTextColor(resources.getColor(R.color.white))
                txt_result.text = "Player 1 Move"
            }else if (aktifPlayer ==2){
                txt_result.setTextColor(resources.getColor(R.color.White))
                txt_result.text = "Player 2 Move"
            }

        btn_restart.visibility = View.INVISIBLE
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
}