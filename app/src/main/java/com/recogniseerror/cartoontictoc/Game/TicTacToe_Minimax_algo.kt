package com.recogniseerror.cartoontictoc.Game

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.recogniseerror.cartoontictoc.Board
import com.recogniseerror.cartoontictoc.Cell
import com.recogniseerror.cartoontictoc.R
import kotlinx.android.synthetic.main.activity_tic_tac_toe_minimax_algo.*

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

        X = R.drawable.x1
        O = R.drawable.o1

        val intent = intent

        imageHint = intent.getStringExtra("Piece Image")
        name = intent.getStringExtra("name")

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
                    .setPositiveButton("yes") { dialogInterface, i -> super.onBackPressed() }
                    .setNegativeButton("no", null)
                    .create()
            dialog.show()
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val dialog: AlertDialog = AlertDialog.Builder(this)
                .setMessage("Are you sure! Are you ready to lose the game?")
                .setPositiveButton("yes") { dialogInterface, i -> super.onBackPressed() }
                .setNegativeButton("no", null)
                .create()
        dialog.show()
    }

    //function is mapping
    //the internal board to the ImageView array board
    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(user!!)
                        boardCells[i][j]?.setPadding(25, 25, 25, 25)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER -> {
                        // For click sound
                        Handler(Looper.getMainLooper()).postDelayed({
                            boardCells[i][j]?.setImageResource(computer!!)
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
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, R.color.text))

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
                    gameWinDialog("$name Won!")
                }
                board.hasPlayerWon() -> {
                    Toast.makeText(this@TicTacToe_Minimax_algo, "You Won", Toast.LENGTH_SHORT).show()
                    gameWinDialog("You Won!")
                }
                board.isGameOver -> {
                    Toast.makeText(this@TicTacToe_Minimax_algo, "Game Tied", Toast.LENGTH_SHORT).show()
                    gameWinDialog("Game Tied")
                }

            }
        }
    }

    private fun gameWinDialog(s: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("   $s")
                .setPositiveButton("Back Home") { dialog: DialogInterface?, which: Int -> super.onBackPressed() }
                .setIcon(R.drawable.logo_game)
                .show()
    }

}
