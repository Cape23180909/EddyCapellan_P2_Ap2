package edu.ucne.eddycapellan_p2_ap2.data.local.repository


import edu.ucne.eddycapellan_p2_ap2.remote.DataSource
import edu.ucne.eddycapellan_p2_ap2.remote.GitHubApi
import edu.ucne.eddycapellan_p2_ap2.remote.Resource
import edu.ucne.eddycapellan_p2_ap2.remote.dto.ContribuidoreDto
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val dataSource: DataSource
) {
    fun getApi(): Flow<Resource<List<RepositoryDto>>> = flow {
        try {
            emit(Resource.Loading())
            val repos = dataSource.listRepos()
            emit(Resource.Success(repos))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
}