package dev.samu.hiittimer.screens

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import dev.samu.hiittimer.R
import dev.samu.hiittimer.CounterDown
import dev.samu.hiittimer.navigate.AppScreens

@Composable
fun Rest(navController: NavController) {
    var theCounter by remember { mutableStateOf(String.format("%02d:%02d", tiempoRest / 60, tiempoRest % 60)) }
    var currentIcon by remember { mutableStateOf(R.drawable.button_start) }
    var isNavigating by remember { mutableStateOf(false) }

    val miCounterDown = remember {
        CounterDown(tiempoRest) { newValue ->
            theCounter = newValue
            CoroutineScope(Dispatchers.Main).launch {
                if (newValue == "00:00" && !isNavigating) {
                    isNavigating = true
                    delay(1000) // Espera 1 segundo (1000 milisegundos)
                    if ((sets - 1) > 0) {
                        // Si quedan sets, iniciar el siguiente ciclo
                        navController.navigate(AppScreens.Work.route)
                    } else {
                        tiempoWork = 0
                        tiempoRest = 0
                        // Si no quedan sets, volver a la pantalla principal
                        navController.popBackStack(AppScreens.PantallaPrincipal.route, false)
                    }
                    sets--
                }
            }
        }
    }

    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Efecto que se lanza al montar el componente
    DisposableEffect(Unit) {
        // Crear y empezar el MediaPlayer
        mediaPlayer = MediaPlayer.create(context, R.raw.descanso)
        mediaPlayer?.start()

        // Iniciar el temporizador
        miCounterDown.start()

        // Limpieza cuando el Composable se desmonta
        onDispose {
            miCounterDown.pause()  // Detener el temporizador
            mediaPlayer?.stop()     // Detener el sonido
            mediaPlayer?.release()  // Liberar recursos del MediaPlayer
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEFA)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sets: $sets", color = Color.White, fontSize = 30.sp)
        Text(text = theCounter, color = Color.White, fontSize = 80.sp)
        Text(
            text = "Rest",
            fontSize = 50.sp,
            color = Color.White,
            modifier = Modifier.padding(36.dp).graphicsLayer(alpha = 0.5f)
        )

        Row {
            Button(
                onClick = {
                    miCounterDown.toggle()
                    currentIcon =
                        if (currentIcon == R.drawable.button_start) R.drawable.button_pause else R.drawable.button_start
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.size(70.dp)
            ) {
                Image(painter = painterResource(currentIcon), contentDescription = null)
            }
            Spacer(modifier = Modifier.size(16.dp))
            // Botón de reset
            Button(
                onClick = {
                    miCounterDown.reset() // Llama al método reset
                },
                modifier = Modifier.size(70.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.White),

                ) {
                Image(painter = painterResource(R.drawable.button_reset), contentDescription = null)
            }
        }
    }
}