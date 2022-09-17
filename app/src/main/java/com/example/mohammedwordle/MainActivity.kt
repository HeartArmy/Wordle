package com.example.mohammedwordle

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomWord = FourLetterWordList.getRandomFourLetterWord() // this is where the random word is stored
        val displayGuess = findViewById<TextView>(R.id.guesses_view) // this links with the guess screen
        val correctAnswer = findViewById<TextView>(R.id.dev_answer_show) // this links with the answer display
        val mainView =
            findViewById<TextView>(
                R.id.main_view
            ) // showing the primary info on display
        val debugView =
            findViewById<TextView>(
                R.id.DEBUG_view_word
            ) // to help show us the correct word
        val inputUserButton = findViewById<Button>(R.id.btn_get_user_input) // button to get user input
        val inputUserText = findViewById<EditText>(R.id.et_user_input) // user input text

        var limitOfGuess = 3 // setting attempts number
        var displayGuessResult = "" 
        var displayInput = "" // displaying user input

        // button code here
        inputUserButton.setOnClickListener {
            // resetting when user wants to
            if (limitOfGuess == 0) {
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            // checking how many guesses are remaining
            if (limitOfGuess != 0) {
                correctAnswer.setTextColor(Color.parseColor("#D3D3D3"))
                correctAnswer.text = "The correct word is $randomWord"
                var isCorrect = false //keeps track of correct/incorrect guess

                val inputUserTextString =
                    inputUserText.text
                        .toString()
                        .uppercase(Locale.getDefault()) // converting here to align with EditText

                if (inputUserTextString.length == 4) {
                    mainView.text = ""
                    val result = checkGuess(inputUserTextString, randomWord)
                    checkGuess(inputUserTextString, randomWord)

                    // displaying first guess
                    if (displayGuessResult == "") {
                        displayGuessResult = result
                        displayInput = inputUserTextString
                    }
                    else { // displaying guesses
                        displayGuessResult = displayGuessResult + "\n" + result
                        displayInput = displayInput + "\n" + inputUserTextString
                    }

                    displayGuess.text = displayGuessResult
                    debugView.text = displayInput
                    // when the guess is correct
                    if (result == "OOOO") {
                        mainView.text = "Nice One! The word is indeed Answer: $randomWord!"
                        inputUserButton.text = "Reset"
                        isCorrect = true
                        limitOfGuess = 0
                    } else { // when the input is not correct after 3 tries
                        limitOfGuess--
                        if (limitOfGuess == 0 && !isCorrect) {
                            mainView.text = "Sorry! You have run out of attempts! \nAnswer: $randomWord"
                            inputUserButton.text = "Reset"
                        }
                    }
                }

                // when the input is not 4 words
                else mainView.text = "Make sure to enter a four letter word!"
            }
        }
    }

    // method that was given to us to check guess
    private fun checkGuess(guess: String, randomWord: String): String {
        var result = ""
        for (i in 0..3) {
            result +=
                if (guess[i] == randomWord[i]) {
                    "O"
                } else if (guess[i] in randomWord) {
                    "+"
                } else {
                    "X"
                }
        }
        return result
    }
}
