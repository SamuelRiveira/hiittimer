package dev.samu.hiittimer

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log

class CounterDown(var segundos: Int, pitido: Boolean = false, val sonido: MediaPlayer? = null, var loquehacealhacertick: (String) -> Unit) {
    private var counterState: Boolean = false  // Indica si el temporizador está corriendo
    private var remainingTime: Long = ((segundos + 1) * 1000L)  // Tiempo restante en milisegundos
    private val initialTime: Long = remainingTime  // Guarda el tiempo inicial
    val pitido = pitido

    private var myCounter: CountDownTimer? = null

    init {
        createTimer(remainingTime)
    }

    // Función para crear el temporizador con un tiempo específico
    private fun createTimer(timeInMillis: Long) {
        myCounter = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                if (pitido) {
                    Log.i("errorr", "Entra")
                    sonido?.start()
                }
                loquehacealhacertick(formatTime(millisUntilFinished / 1000))
            }

            override fun onFinish() {
                counterState = false
                loquehacealhacertick(formatTime(0))
            }
        }
    }

    // Alternar entre iniciar, pausar y reanudar
    fun toggle() {
        if (counterState) {
            pause()
        } else {
            if (remainingTime > 0) {
                start()
            }
        }
    }

    // Iniciar el temporizador desde el principio
    fun start() {
        counterState = true
        createTimer(remainingTime)  // Crea un temporizador nuevo desde el tiempo restante
        myCounter?.start()
    }

    // Pausar el temporizador
    fun pause() {
        counterState = false
        myCounter?.cancel()
    }

    // Función para resetear el temporizador
    fun reset() {
        pause()  // Detener el temporizador si está corriendo
        remainingTime = initialTime  // Restablecer el tiempo restante al tiempo inicial
        loquehacealhacertick(formatTime(initialTime / 1000))  // Actualizar la visualización al tiempo inicial
        counterState = false  // Asegurarse de que el estado sea "no corriendo"
        myCounter?.start()
    }

    // Función para formatear el tiempo a "MM:SS"
    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
}
