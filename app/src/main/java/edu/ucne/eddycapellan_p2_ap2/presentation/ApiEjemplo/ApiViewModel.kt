package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.eddycapellan_p2_ap2.data.local.repository.ApiRepository
import edu.ucne.eddycapellan_p2_ap2.remote.Resource
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoryUiState())
    val uiState: StateFlow<RepositoryUiState> = _uiState.asStateFlow()

    init {
        fetchRepositories("enelramon") // Puedes cambiar por un usuario por defecto
    }

    fun fetchRepositories(username: String) {
        viewModelScope.launch {
            repository.listRepos(username).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                repositories = result.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message ?: "Error desconocido al obtener repositorios"
                            )
                        }
                    }
                }
            }
        }
    }

    fun setSelectedRepository(repo: RepositoryDto?) {
        _uiState.update {
            it.copy(
                name = repo?.name,
                description = repo?.description,
                htmlUrl = repo?.htmlUrl,
                inputError = null
            )
        }
    }

    fun setName(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun setDescription(value: String?) {
        _uiState.update { it.copy(description = value) }
    }

    fun setHtmlUrl(value: String) {
        _uiState.update { it.copy(htmlUrl = value) }
    }

    fun saveRepository() {
        if (!validateFields()) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                // Aquí iría la lógica para guardar el repositorio
                // Por ejemplo:
                // val repo = RepositoryDto(
                //     name = _uiState.value.name!!,
                //     description = _uiState.value.description,
                //     htmlUrl = _uiState.value.htmlUrl!!
                // )
                // repository.saveRepository(repo)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        inputError = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        return when {
            _uiState.value.name.isNullOrBlank() -> {
                _uiState.update { it.copy(inputError = "El nombre no puede estar vacío") }
                false
            }
            _uiState.value.htmlUrl.isNullOrBlank() -> {
                _uiState.update { it.copy(inputError = "La URL no puede estar vacía") }
                false
            }
            else -> {
                _uiState.update { it.copy(inputError = null) }
                true
            }
        }
    }

    fun clearFields() {
        _uiState.update {
            it.copy(
                name = null,
                description = null,
                htmlUrl = null,
                inputError = null
            )
        }
    }

    fun clearErrors() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                inputError = null
            )
        }
    }
}