package edu.ucne.eddycapellan_p2_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ApiListScreen : Screen()
    @Serializable
    data class ApiScreen(val name: String?) : Screen()
}