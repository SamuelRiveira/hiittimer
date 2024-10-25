package dev.samu.hiittimer.screens

import android.media.MediaPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import dev.samu.hiittimer.R
import dev.samu.hiittimer.navigate.AppScreens

var sets: Int = 0
var tiempoWork: Int = 0
var tiempoRest: Int = 0
var setsFinalizados = false

@Composable
fun PantallaPrincipal(navController: NavController) {
    var timeWork by remember { mutableStateOf(0) }
    var timeRest by remember { mutableStateOf(0) }
    var timeSets by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    if (setsFinalizados){
        mediaPlayer = MediaPlayer.create(context, R.raw.finalizado)
        mediaPlayer?.start()
        setsFinalizados = false
    }

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
                        navController.navigate(AppScreens.GetReady.route)
                    }
                ) {
                    Text(text = "⚡ START", fontSize = 20.sp)
                }
            }
        }
    }
}