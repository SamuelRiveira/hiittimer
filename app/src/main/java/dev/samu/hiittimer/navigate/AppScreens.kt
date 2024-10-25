package dev.samu.hiittimer.navigate

sealed class AppScreens(val route: String) {
    // Pantallas
    object PantallaPrincipal: AppScreens("pantalla_principal")
    object GetReady: AppScreens("get_ready")
    object Rest: AppScreens("rest")
    object Work: AppScreens("work")
}