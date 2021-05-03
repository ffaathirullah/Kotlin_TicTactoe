package org.d3ifcool.tic_ta_toe.playerVComp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_player_w_comp.*
import org.d3ifcool.tic_ta_toe.R
import kotlin.random.Random

class PlayerWComp : AppCompatActivity() {

    var board = BoardComp()
    private val cell = Array(3) { arrayOfNulls<ImageView>(3) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_w_comp)
        Board()

        btn_restart_comp.setOnClickListener {
            board = BoardComp()
            text_result_comp.text = ""
            mapBaord()
        }
    }

    private fun Board() {

        for (i in cell.indices) {
            for (j in cell.indices) {
                cell[i][j] = ImageView(this)
                cell[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 150
                    height = 100
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                cell[i][j]?.background = resources.getDrawable(R.drawable.kotak)
                cell[i][j]?.setOnClickListener(CellClickListener(i, j))
                board_layout.addView(cell[i][j])
            }
        }
    }


    fun mapBaord() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    BoardComp.Player -> {
                        cell[i][j]?.setImageResource(R.drawable.alpha_s_comp)
                        cell[i][j]?.isEnabled = false
                    }

                    BoardComp.Comp -> {
                        cell[i][j]?.setImageResource(R.drawable.alpha_o_comp)
                        cell[i][j]?.isEnabled = false
                    }
                    else -> {
                        cell[i][j]?.setImageResource(0)
                        cell[i][j]?.isEnabled = true
                    }
                }
            }
        }
     }

    inner class CellClickListener(
        private val i: Int,
        private val j: Int
    ) : View.OnClickListener {

        override fun onClick(p0: View?) {

            if (!board.gameOver) {
                val cell = Cell(i, j)
                board.placeMove(cell, BoardComp.Player)
                mapBaord()

                if (board.cellYangAda.isNotEmpty()) {
                    val cCells = board.cellYangAda[Random.nextInt(0, board.cellYangAda.size)]
                    board.placeMove(cCells, BoardComp.Comp)
                }
                mapBaord()
            }

            when {
                board.computerWon() -> text_result_comp.text = "Computer Won !!!"
                board.playerWon() -> text_result_comp.text = "Player Won !!!"
                board.gameOver -> text_result_comp.text = "Game Tie !!!d"
            }
        }
    }
}