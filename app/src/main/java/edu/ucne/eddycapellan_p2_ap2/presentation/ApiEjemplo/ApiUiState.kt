package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo

import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto


data class ApiUiState(
    val name: String = "",
    val description: String = "",
    val htmlUrl: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val api: List<RepositoryDto> = emptyList(),
    val inputError: String? = null
)