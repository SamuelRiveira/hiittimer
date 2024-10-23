package dev.samu.hiittimer

import android.os.CountDownTimer
import android.util.Log

class CounterDown(var segundos: Int, var loquehacealhacertick: (String) -> Unit) {
    private var counterState: Boolean = false
    private var remainingTime: Long = (segundos * 1000L)  // Tiempo restante en milisegundos
    private var myCounter: CountDownTimer? = null

    init {
        createTimer(remainingTime)
    }

    private fun createTimer(timeInMillis: Long) {
        myCounter = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                loquehacealhacertick(formatTime(millisUntilFinished / 1000))
            }

            override fun onFinish() {
                counterState = false
                loquehacealhacertick(formatTime(0))
                Log.i("dam2", "Timer finished")
            }
        }
    }

    fun toggle() {
        if (counterState) {
            pause()
        } else {
            if (remainingTime > 0) {
                start()
            }
        }
    }

    fun start() {
        counterState = true
        createTimer(remainingTime)  // Reinicia el temporizador desde el tiempo restante
        myCounter?.start()
    }

    fun pause() {
        counterState = false
        myCounter?.cancel()  // Cancelamos el temporizador
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
}
