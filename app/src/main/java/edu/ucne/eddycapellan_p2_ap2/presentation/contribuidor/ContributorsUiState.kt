package edu.ucne.eddycapellan_p2_ap2.presentation.contribuidor

import edu.ucne.eddycapellan_p2_ap2.remote.dto.ContribuidoreDto

data class ContributorsUiState(
    val contributors: List<ContribuidoreDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)