package edu.ucne.eddycapellan_p2_ap2.presentation.contribuidor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.eddycapellan_p2_ap2.data.local.repository.ContribuidorRepository
import edu.ucne.eddycapellan_p2_ap2.remote.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContribuidorViewModel @Inject constructor (
    private val repository: ContribuidorRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ContributorsUiState())
    val uiState: StateFlow<ContributorsUiState> = _uiState.asStateFlow()

    fun getContributors(owner: String, repo: String) {
        viewModelScope.launch {
            repository.getContributors(owner, repo).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            contributors = resource.data ?: emptyList(),
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "Error desconocido"
                        )
                    }
                }
            }
        }
    }
}