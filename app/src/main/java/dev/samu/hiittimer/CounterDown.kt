package dev.samu.hiittimer

import android.os.CountDownTimer
import android.util.Log

class CounterDown(var segundos: Int, var loquehacealhacertick: (String) -> Unit) {
    private var counterState: Boolean = false  // Indica si el temporizador está corriendo
    private var remainingTime: Long = ((segundos + 1) * 1000L)  // Tiempo restante en milisegundos
    var isStarted: Boolean = false  // Indica si el temporizador ya ha empezado
    var isPaused: Boolean = false  // Indica si está pausado

    private var myCounter: CountDownTimer? = null

    init {
        createTimer(remainingTime)
    }

    // Función para crear el temporizador con un tiempo específico
    private fun createTimer(timeInMillis: Long) {
        myCounter = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                if (counterState) {
                    loquehacealhacertick(formatTime(millisUntilFinished / 1000))
                }
            }

            override fun onFinish() {
                counterState = false
                isStarted = false
                remainingTime = 0
                loquehacealhacertick(formatTime(0))  // Mostrar 00:00 cuando termine
                Log.i("dam2", "Timer finished")
            }
        }
    }

    // Alternar entre iniciar, pausar y reanudar
    fun toggle() {
        Log.i("dam2", "toggle: $counterState")
        if (counterState) {
            pause()
        } else {
            if (!isStarted) {
                start()
            } else {
                resume()
            }
        }
    }

    // Iniciar el temporizador desde el principio
    fun start() {
        counterState = true
        isStarted = true
        isPaused = false
        createTimer(remainingTime)  // Crea un temporizador nuevo desde el tiempo restante
        myCounter?.start()
    }

    // Pausar el temporizador
    fun pause() {
        counterState = false
        myCounter?.cancel()  // Cancelamos el temporizador, pero mantenemos remainingTime
        isPaused = true
        Log.i("dam2", "Paused at: ${formatTime(remainingTime / 1000)}")
    }

    // Reanudar el temporizador desde el tiempo restante
    fun resume() {
        if (isPaused) {
            counterState = true
            createTimer(remainingTime)  // Recreamos el temporizador con el tiempo restante
            myCounter?.start()
            Log.i("dam2", "Resumed at: ${formatTime(remainingTime / 1000)}")
            isPaused = false
        }
    }

    // Función para formatear el tiempo a "MM:SS"
    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
}
