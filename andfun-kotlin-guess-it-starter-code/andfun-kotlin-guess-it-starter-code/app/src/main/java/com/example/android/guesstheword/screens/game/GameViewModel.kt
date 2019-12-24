package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){

    companion object{

        // This is when the game is over
        private const val DONE = 0L

        // This is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L

        // This is the total time of the game
        private const val COUNTDOWN_TIME = 60000L
    }



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
    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime : LiveData<Long>
        get() = _currentTime

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

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish

    init {
        Log.i("GameViewModel", "GameViewModel Created!")

        resetList()
        nextWord()

        _score.value = 0
        _eventGameFinish.value = false

        // Creates a timer which triggers the end of the game when it finishes
        // COUNTDOWN_TIME = Total Time. ONE_SECOND = Amount of time for a tick.
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        timer.start()

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
            resetList()
        }
        _word.value = wordList.removeAt(0)
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

    // Prevents the event from happening more than once
    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer.cancel()
    }
}