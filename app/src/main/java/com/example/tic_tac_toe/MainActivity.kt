package com.example.tic_tac_toe

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var playerOneTurn=true
    private var playerOneMoves=mutableListOf<Int>()
    private var playerTwoMoves=mutableListOf<Int>()


    private val possibleWins:Array<List<Int>> = arrayOf(

        //horizontal lines
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),

        //vertical lines
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(3, 6, 9),

        //diagonal lines
        listOf(1, 5, 9),
        listOf(3, 5, 7)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard()
    {
        var counter=1;

        for(i in 1..3)
        {
            // creating a linear layout manually in mainActivity and then assigning the parameters
            val linearLayout=LinearLayout(this)
            linearLayout.orientation=LinearLayout.HORIZONTAL
            val params1=LinearLayout.LayoutParams(MATCH_PARENT,0,1.0F)
            linearLayout.layoutParams=params1

            for(j in 1..3)
            {
                val button=Button(this)
                button.id=counter
                button.textSize=42.0F
                button.setTextColor(ContextCompat.getColor(this, R.color.purple_500))
                //button.setTextColor(0xFF6200EE);

                val params2=LinearLayout.LayoutParams(0, MATCH_PARENT,1.0F)
                button.layoutParams=params2
               button.setOnClickListener{
                   recordMove(it)
               }
               linearLayout.addView(button)
               counter++;


            }
            val p:LinearLayout=findViewById(R.id.board)
            p.addView(linearLayout)

        }
    }
    private fun recordMove(view: View)
    {
        val button:Button= view as Button
        val id=button.id

        if(playerOneTurn)
        {
            playerOneMoves.add(id)
            button.text="0"
            button.isEnabled=false

            if(checkWin(playerOneMoves))
            {
                showWinMessage(findViewById(R.id.player_one))
            }
            else
            {
                playerOneTurn=false

                togglePlayerTurn(findViewById(R.id.player_tow_label),findViewById(R.id.palyer_one_label))
            }
        }
        else
        {
            playerTwoMoves.add(id)

            button.text = "X"
            button.isEnabled = false
            if(checkWin(playerTwoMoves))
            {
                showWinMessage(findViewById(R.id.player_two))
            } else{
                playerOneTurn = true
                togglePlayerTurn(findViewById(R.id.palyer_one_label),findViewById(R.id.player_tow_label))
            }
        }
    }
    private fun checkWin(moves :List<Int>):Boolean{

        var won=false;
        if(moves.size>=3)
        {
            for(i in possibleWins)
            {
                if(moves.containsAll(i))
                {
                    won=true
                    return won
                }
            }
        }
        return won
    }

    private fun showWinMessage(player:TextView){
        var playerName = player.text.toString()
        if(playerName.isBlank()){
            playerName = player.hint.toString()
        }
        Toast.makeText(this, "Congratulations! $playerName You Won", Toast.LENGTH_SHORT).show()

    }
    private fun togglePlayerTurn(playerOn: TextView, playerOff: TextView){
        playerOff.setTextColor(
            ContextCompat.getColor(this, R.color.teal_700))
        playerOff.setTypeface(null, Typeface.NORMAL)
        playerOn.setTextColor(
            ContextCompat.getColor(this, R.color.teal_700))
        playerOn.setTypeface(null, Typeface.BOLD)
    }

}