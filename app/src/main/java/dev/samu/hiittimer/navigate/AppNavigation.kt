package dev.samu.hiittimer.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.samu.hiittimer.screens.GetReady
import dev.samu.hiittimer.screens.PantallaPrincipal
import dev.samu.hiittimer.screens.Rest
import dev.samu.hiittimer.screens.Work

@Composable
fun AppNavigation(modifier: androidx.compose.ui.Modifier){
    // estado de gestion de navegación
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.PantallaPrincipal.route) {
        composable(route = AppScreens.PantallaPrincipal.route){
            PantallaPrincipal(navController)
        }
        composable(route = AppScreens.GetReady.route) {
            GetReady(navController)
        }
        composable(route = AppScreens.Rest.route) {
            Rest(navController)
        }
        composable(route = AppScreens.Work.route) {
            Work(navController)
        }
    }
}