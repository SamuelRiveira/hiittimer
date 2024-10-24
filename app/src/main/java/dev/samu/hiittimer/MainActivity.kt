package dev.samu.hiittimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import dev.samu.hiittimer.ui.theme.HIITTimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HIITTimerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

var sets: Int = 0
var tiempoWork: Int = 0
var tiempoRest: Int = 0

@Composable
fun PantallaPrincipal(
    modifier: Modifier,
    onStartClicked: () -> Unit // Función que se llama cuando se presiona el botón
) {

    var timeWork by remember { mutableStateOf(0) }
    var timeRest by remember { mutableStateOf(0) }
    var timeSets by remember { mutableStateOf(0) }

    Column {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
        ) {
            Text(text = "Tabata", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            //Sets
            Row {
                Column(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Sets",
                            fontSize = 25.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp
                                )

                        )
                    }
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Row {
                            Icon(
                                painter = painterResource(R.drawable.arriba),
                                contentDescription = "Flecha hacia abajo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        sets++
                                        timeSets++
                                    }
                            )
                        }

                        Row {
                            Text(
                                text = "$timeSets",
                                fontSize = 28.sp
                            )
                        }

                        Row {
                            Icon(
                                painter = painterResource(R.drawable.abajo),
                                contentDescription = "Flecha hacia arriba",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        if (sets > 0) {
                                            sets--
                                            timeSets--
                                        }
                                    }
                            )
                        }
                    }
                }
            }
            //Work
            Row {
                Column(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Work",
                            fontSize = 25.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )
                    }
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Row {
                            Icon(
                                painter = painterResource(R.drawable.arriba),
                                contentDescription = "Flecha hacia arriba",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        tiempoWork += 60
                                        timeWork += 60
                                    }
                            )
                            Icon(
                                painter = painterResource(R.drawable.arriba),
                                contentDescription = "Flecha hacia abajo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        tiempoWork++
                                        timeWork++
                                    }
                            )
                        }

                        Row {
                            Text(
                                text = String.format("%02d:%02d", timeWork / 60, timeWork % 60),
                                fontSize = 28.sp
                            )
                        }

                        Row {
                            Icon(
                                painter = painterResource(R.drawable.abajo),
                                contentDescription = "Flecha hacia abajo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        if (tiempoWork >= 60) {
                                            tiempoWork -= 60
                                            timeWork -= 60
                                        }
                                    }
                            )
                            Icon(
                                painter = painterResource(R.drawable.abajo),
                                contentDescription = "Flecha hacia arriba",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        if (tiempoWork > 0) {
                                            tiempoWork --
                                            timeWork--
                                        }
                                    }
                            )
                        }
                    }
                }
            }
            //Rest
            Row {
                Column(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Rest",
                            fontSize = 25.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )
                    }
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Row {
                            Icon(
                                painter = painterResource(R.drawable.arriba),
                                contentDescription = "Flecha hacia arriba",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        tiempoRest += 60
                                        timeRest += 60
                                    }
                            )
                            Icon(
                                painter = painterResource(R.drawable.arriba),
                                contentDescription = "Flecha hacia abajo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        tiempoRest++
                                        timeRest++
                                    }
                            )
                        }

                        Row {
                            Text(
                                text = String.format("%02d:%02d", timeRest / 60, timeRest % 60),
                                fontSize = 28.sp
                            )
                        }

                        Row {
                            Icon(
                                painter = painterResource(R.drawable.abajo),
                                contentDescription = "Flecha hacia abajo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        if (tiempoRest >= 60) {
                                            tiempoRest -= 60
                                            timeRest -= 60
                                        }
                                    }
                            )
                            Icon(
                                painter = painterResource(R.drawable.abajo),
                                contentDescription = "Flecha hacia arriba",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        if (tiempoRest > 0) {
                                            tiempoRest--
                                            timeRest--
                                        }
                                    }
                            )
                        }
                    }
                }
            }
            //Button
            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onStartClicked()
                    }
                ) {
                    Text(text = "⚡ START", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier) {
    var showWork by remember { mutableStateOf(false) }
    var showPantallaPrincipal by remember { mutableStateOf(true) }
    var showGetReady by remember { mutableStateOf(false) }
    var remainingSets by remember { mutableStateOf(sets) }
    var isLastSet by remember { mutableStateOf(false) }
    var resetCompleted by remember { mutableStateOf(false) }

    // Para manejar la finalización del trabajo
    val handleWorkCompletion = {
        if (remainingSets > 0) {
            showWork = false
            if (remainingSets == 1) {
                isLastSet = true
            }
        }
    }

    // Para manejar la finalización del rest
    val handleRestCompletion = {
        if (!isLastSet) {
            if (!resetCompleted) {
                remainingSets--
                sets--
                resetCompleted = true
            }
            showWork = true
        } else {
            remainingSets = sets
            isLastSet = false
            showPantallaPrincipal = true
            resetCompleted = false
        }
    }

    // Manejo del GetReady
    val handleGetReadyCompletion = {
        showGetReady = false
        showWork = true
    }

    if (showPantallaPrincipal) {
        PantallaPrincipal(
            modifier = modifier,
            onStartClicked = {
                remainingSets = sets
                showPantallaPrincipal = false
                showGetReady = true
                resetCompleted = false
            }
        )
        sets = 0
        tiempoWork = 0
        tiempoRest = 0
    } else {
        if (showGetReady) {
            GetReady(modifier = Modifier, onGetReadyCompleted = handleGetReadyCompletion)
        } else if (showWork) {
            Work(modifier = Modifier, onWorkCompleted = handleWorkCompletion)
        } else {
            Rest(modifier = Modifier, onRestCompleted = handleRestCompletion)
        }
    }
}


@Composable
fun GetReady(
    modifier: Modifier = Modifier,
    onGetReadyCompleted: () -> Unit // Callback para cuando termine el tiempo de GetReady
) {
    val tiempoGetReady = 10
    var theCounter by remember { mutableStateOf(String.format("%02d:%02d", tiempoGetReady / 60, tiempoGetReady % 60)) }

    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.pitidoporsegundo) }

    val miCounterDown = remember {
        CounterDown(tiempoGetReady, true, sonido = mediaPlayer) { newValue ->
            theCounter = newValue
            CoroutineScope(Dispatchers.Main).launch {
                if (newValue == "00:00") {
                    delay(1000) // Espera 1 segundo (1000 milisegundos)
                    onGetReadyCompleted() // Llama a onWorkCompleted después de la espera
                }
            }
        }
    }


    val handler = Handler(Looper.getMainLooper())
    var repetitionCount = 0

    LaunchedEffect(Unit) {
        mediaPlayer.setOnCompletionListener {
            repetitionCount++
            if (repetitionCount < tiempoGetReady) {
                handler.postDelayed({
                    mediaPlayer.start()
                }, 0) // Retraso de 500 ms antes de la siguiente repetición
            } else {
                mediaPlayer.release() // Libera el media player después de usarlo
            }
        }

        mediaPlayer.start() // Inicia la primera reproducción
    }

    miCounterDown.start()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9800)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = theCounter,
            color = Color.White,
            fontSize = 80.sp
        )
        Text(
            text = "Get Ready",
            color = Color.White,
            fontSize = 60.sp,
            modifier = Modifier
                .padding(36.dp)
                .graphicsLayer(alpha = 0.5f)
        )
    }
}

@Composable
fun Work(
    modifier: Modifier = Modifier,
    onWorkCompleted: () -> Unit // Callback para cuando termine el tiempo de Work
) {
    var theCounter by remember { mutableStateOf(String.format("%02d:%02d", tiempoWork / 60, tiempoWork % 60)) }

    var currentIcon by remember { mutableStateOf(R.drawable.button_start) }

    val miCounterDown = remember {
        CounterDown(tiempoWork) { newValue ->
            theCounter = newValue
            CoroutineScope(Dispatchers.Main).launch {
                if (newValue == "00:00") {
                    delay(1000) // Espera 1 segundo (1000 milisegundos)
                    onWorkCompleted() // Llama a onWorkCompleted después de la espera
                }
            }
        }
    }

    val context = LocalContext.current

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    mediaPlayer = MediaPlayer.create(context, R.raw.trabajo)

    LaunchedEffect(Unit) {
        mediaPlayer?.start()
        miCounterDown.start()
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC876FF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sets: $sets",
            color = Color.White,
            fontSize = 30.sp
        )
        Text(
            text = theCounter,
            color = Color.White,
            fontSize = 80.sp
        )
        Text(
            text = "¡Let's go!",
            color = Color.White,
            fontSize = 60.sp,
            modifier = Modifier
                .padding(36.dp)
                .graphicsLayer(alpha = 0.5f)
        )
        Button(
            onClick = {
                miCounterDown.toggle()

                if (currentIcon == R.drawable.button_start) {
                    currentIcon = R.drawable.button_pause
                } else {
                    currentIcon = R.drawable.button_start
                }
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                Color.White
            ),
            modifier = Modifier
                .size(70.dp)
        ) {
            Image(
                painter = painterResource(currentIcon),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Rest(
    modifier: Modifier = Modifier,
    onRestCompleted: () -> Unit // Callback para cuando termine el tiempo de Reset
) {
    var theCounter by remember { mutableStateOf(String.format("%02d:%02d", tiempoRest / 60, tiempoRest % 60)) }

    var currentIcon by remember { mutableStateOf(R.drawable.button_start) }

    val miCounterDown = remember {
        CounterDown(tiempoRest) { newValue ->
            theCounter = newValue
            CoroutineScope(Dispatchers.Main).launch {
                if (newValue == "00:00") {
                    delay(1000) // Espera 1 segundo (1000 milisegundos)
                    onRestCompleted() // Cuando el contador llegue a 0, llamamos a onResetCompleted
                }
            }
        }
    }

    val context = LocalContext.current

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    mediaPlayer = MediaPlayer.create(context, R.raw.descanso)

    LaunchedEffect(Unit) {
        mediaPlayer?.start()
        miCounterDown.start()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEFA)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sets: $sets",
            color = Color.White,
            fontSize = 30.sp
        )
        Text(
            text = theCounter,
            color = Color.White,
            fontSize = 80.sp
        )
        Text(
            text = "Rest",
            fontSize = 50.sp,
            color = Color.White,
            modifier = Modifier
                .padding(36.dp)
                .graphicsLayer(alpha = 0.5f)
        )

        Button(
            onClick = {
                miCounterDown.toggle()

                if (currentIcon == R.drawable.button_start) {
                    currentIcon = R.drawable.button_pause
                } else {
                    currentIcon = R.drawable.button_start
                }
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                Color.White
            ),
            modifier = Modifier
                .size(70.dp)
        ) {
            Image(
                painter = painterResource(currentIcon),
                contentDescription = null
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppContentPreview(){
    AppContent(modifier = Modifier)
}
