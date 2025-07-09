package edu.ucne.eddycapellan_p2_ap2.data.local.repository


import edu.ucne.eddycapellan_p2_ap2.remote.GitHubApi
import edu.ucne.eddycapellan_p2_ap2.remote.Resource
import edu.ucne.eddycapellan_p2_ap2.remote.dto.ContribuidoreDto
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api: GitHubApi
) {
    fun listRepos(username: String): Flow<Resource<List<RepositoryDto>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.listRepos(username)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error al obtener los repositorios"))
        }
    }

    suspend fun createRepository(repo: RepositoryDto): Flow<Resource<RepositoryDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = api.createRepository(repo)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error desconocido"))
        }
    }

    fun getContributors(owner: String, repo: String): Flow<Resource<List<ContribuidoreDto>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getContributors(owner, repo)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error al obtener contribuyentes"))
        }
    }

}