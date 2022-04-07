package com.example.tictoctoe.Game

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tictoctoe.Board
import com.example.tictoctoe.Cell
import com.example.tictoctoe.R
import kotlinx.android.synthetic.main.activity_tic_tac_toe_minimax_algo.*
import java.util.*

class TicTacToe_Minimax_algo : AppCompatActivity() {

    //Creating a 2D Array of ImageViews
    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3) }

    //creating the board instance
    var board = Board()

    private var imageHint: String? = null
    private var name: String? = null

    private var X: Int? = 0
    private var O: Int? = 0
    private var user: Int? = 0
    private var computer: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe_minimax_algo)

        //calling the function to load our tic tac toe board
        loadBoard()

        X = R.drawable.fancing
        O = R.drawable.yinyang

        val intent = intent
        val hashMap = intent.getSerializableExtra("hashMap") as HashMap<*, *>?

        imageHint = hashMap?.get("Piece Image") as String?
        name = hashMap?.get("name") as String?

        opponent_name.text = name

        if (imageHint == "X") {
            user = X
            computer = O
            your_cell.setImageResource(X!!)
            opponent_cell.setImageResource(O!!)
        } else {
            user = O
            computer = X
            your_cell.setImageResource(O!!)
            opponent_cell.setImageResource(X!!)
        }

        back.setOnClickListener {
            val dialog: AlertDialog = AlertDialog.Builder(this)
                    .setMessage("Are you sure! Are you ready to lose the game?")
                    .setPositiveButton("yes") { dialogInterface, i -> onBackPressed() }
                    .setNegativeButton("no", null)
                    .create()
            dialog.show()
        }

    }

    //function is mapping
    //the internal board to the ImageView array board
    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.fancing)
                        boardCells[i][j]?.setPadding(25, 25, 25, 25)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER -> {
                        // For click sound
                        Handler(Looper.getMainLooper()).postDelayed({
                            boardCells[i][j]?.setImageResource(R.drawable.yinyang)
                            boardCells[i][j]?.setPadding(25, 25, 25, 25)
                            boardCells[i][j]?.isEnabled = false
                            //media.buttonSound(this)
                        }, 0)

                    }
                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }


    private fun loadBoard() {
        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j] = ImageView(this)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 230
                    bottomMargin = 10
                    topMargin = 10
                    leftMargin = 10
                    rightMargin = 10
                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, R.color.red_light2))

                //attached a click listener to the board
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))

                layout_board.addView(boardCells[i][j])
            }
        }
    }

    inner class CellClickListener(val i: Int, val j: Int) : View.OnClickListener {

        override fun onClick(p0: View?) {

            //checking if the game is not over
            if (!board.isGameOver) {

                //creating a new cell with the clicked index
                val cell = Cell(i, j)

                //placing the move for player
                board.placeMove(cell, Board.PLAYER)

                //calling minimax to calculate the computers move
                board.minimax(0, Board.COMPUTER)

                //performing the move for computer
                board.computersMove?.let {
                    board.placeMove(it, Board.COMPUTER)
                }

                //mapping the internal board to visual board
                mapBoardToUi()
            }

            //Displaying the results
            //according to the game status
            when {

                board.hasComputerWon() -> {
                    Toast.makeText(this@TicTacToe_Minimax_algo, "$name Won", Toast.LENGTH_SHORT).show()
                }
                board.hasPlayerWon() -> {
                    Toast.makeText(this@TicTacToe_Minimax_algo, "You Won", Toast.LENGTH_SHORT).show()
                }
                board.isGameOver -> {
                    Toast.makeText(this@TicTacToe_Minimax_algo, "Game Tied", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}
