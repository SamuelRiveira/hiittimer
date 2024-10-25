package dev.samu.hiittimer.screens

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

import dev.samu.hiittimer.CounterDown
import dev.samu.hiittimer.R
import dev.samu.hiittimer.navigate.AppScreens

@Composable
fun GetReady(navController: NavController) {
    val tiempoGetReady = 5
    var theCounter by remember { mutableStateOf(String.format("%02d:%02d", tiempoGetReady / 60, tiempoGetReady % 60)) }
    var isNavigating by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.pitidoporsegundo) }

    val miCounterDown = remember {
        CounterDown(tiempoGetReady) { newValue ->
            theCounter = newValue
            CoroutineScope(Dispatchers.Main).launch {
                if (newValue == "00:00" && !isNavigating) {
                    isNavigating = true
                    navController.navigate(AppScreens.Work.route)
                }
            }
        }
    }
    var repetitionCount = 0

    LaunchedEffect(Unit) {
        while (repetitionCount < tiempoGetReady) {
            mediaPlayer.start() // Inicia la reproducción del sonido
            repetitionCount++
            delay(1000L) // Espera 1 segundo
            mediaPlayer.pause() // Pausa el audio para reiniciar en el siguiente ciclo
            mediaPlayer.seekTo(0) // Vuelve al inicio del audio
        }
        mediaPlayer.release() // Libera el media player al finalizar
    }

    DisposableEffect(Unit) {
        miCounterDown.start()

        onDispose {
            if (!isNavigating) {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
            miCounterDown.pause()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9800)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = theCounter, color = Color.White, fontSize = 80.sp)
        Text(
            text = "Get Ready",
            color = Color.White,
            fontSize = 60.sp,
            modifier = Modifier.padding(36.dp).graphicsLayer(alpha = 0.5f)
        )
    }
}