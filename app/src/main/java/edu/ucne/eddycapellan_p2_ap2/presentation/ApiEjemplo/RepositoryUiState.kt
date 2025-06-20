package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto


data class RepositoryUiState(
    val isLoading: Boolean = false,
    val repositories: List<RepositoryDto> = emptyList(),
    val name: String? = null,
    val description: String? = null,
    val htmlUrl: String? = null,
    val errorMessage: String? = null,
    val inputError: String? = null
)