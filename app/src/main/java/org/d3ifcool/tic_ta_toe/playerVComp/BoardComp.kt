package org.d3ifcool.tic_ta_toe.playerVComp

class BoardComp {
    companion object {
        const val Player = "S"
        const val Comp = "0"
    }

    val board = Array(3) {
        arrayOfNulls<String>(3)
    }

    val cellYangAda: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    fun placeMove(cell: Cell, player: String) {
        board[cell.i][cell.j] = player
    }

    val gameOver: Boolean
        get() = computerWon() || playerWon() || cellYangAda.isEmpty()


    fun computerWon(): Boolean {
        if (board[0][0] == board[2][2] &&
            board[1][1] == Player &&
            board[0][0] == Comp ||
            board[0][2] == board[2][0] &&
            board[1][1] == Player &&
            board[0][2] == Comp
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][2] &&
                board[i][1] == Player &&
                board[i][0] == Comp ||
                board[0][i] == board[2][i] &&
                board[1][i] == Player &&
                board[0][i] == Comp
            ) {
                return true
            }
        }

        return false
    }

    fun playerWon(): Boolean {

        if (board[0][0] == board[2][2] &&
            board[1][1] == Comp &&
            board[0][0] == Player ||
            board[0][2] == board[2][0]  &&
            board[1][1] == Comp &&
            board[0][2] == Player
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][2] &&
                board[i][1] == Comp &&
                board[i][0] == Player ||
                board[0][i] == board[2][i] &&
                board[1][i] == Comp &&
                board[0][i] == Player
            ) {
                return true
            }
        }

        return false
    }
}