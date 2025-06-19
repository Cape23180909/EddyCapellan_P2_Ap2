package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.eddycapellan_p2_ap2.data.local.repository.ApiRepository
import edu.ucne.eddycapellan_p2_ap2.remote.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApiUiState())
    val uiState: StateFlow<ApiUiState> get() = _uiState

    init {
        getApi()
    }

    fun getApi() {
        viewModelScope.launch {
            repository.getApi(username = String()).collect { result ->
                when (result) {
                    is Resource.Loading<*> -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success<*> -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            api = result.data ?: emptyList(),
                            errorMessage = null
                        )
                    }
                    is Resource.Error<*> -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

//    fun saveApi(repositoryDto: RepositoryDto) {
//        viewModelScope.launch {
//            val exists = _uiState.value.Api.any { it.name == repositoryDto.name }
//            if (exists) {
//                repository.updateApi(repositoryDto)
//            } else {
//                repository.createApi(repositoryDto)
//            }
//            getApis()
//        }
//    }

//    fun deleteApi(name: String) {
//        viewModelScope.launch {
//            repository.deleteApi(name)
//            getApis()
//        }
//    }

//    fun getApiByName(name: String?): RepositoryDto? {
//        return _uiState.value.Api.find { it.name == name }
//    }
}