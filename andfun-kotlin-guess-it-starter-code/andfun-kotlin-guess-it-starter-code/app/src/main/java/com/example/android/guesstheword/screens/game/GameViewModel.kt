package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){

    /*
    ---------------------------------
        IN ORDER TO ACHIEVE encapsulation, use backing properties.
        Backing properties means that there's an internal version and external version of variables.
        _variableName = Internal version of the code. Can be Read and Written within the file.
        variableName = External version of the code. Can only be read.

        YOU WANT TO USE THE INTERNAL VERSION WHEN USING THE VARIABLE INSIDE OF IT'S OWN CLASS.
        YOU WANT TO USE THE EXTERNAL VERSION WHEN USING THE VARIABLE OUTSIDE OF IT'S OWN CLASS.

    ---------------------------------
     */

    // The current word
    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    // The current score. All LiveData start out as null. Therefore initialize it in the init block
    // These three lines of code make it so that _score is known as a MutableLiveData (Can be changed)
    // in the GameViewModel but score is seen as a LiveData (Can't be changed, only read)
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel Created!")

        resetList()
        nextWord()

        _score.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //gameFinished()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun onSkip() {
//        score--
        // Null Check since live data starts out as null, get an error.
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }
}