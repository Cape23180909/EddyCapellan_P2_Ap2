package edu.ucne.eddycapellan_p2_ap2.remote.dto

data class ContribuidoreDto (
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val contributions: Int
)