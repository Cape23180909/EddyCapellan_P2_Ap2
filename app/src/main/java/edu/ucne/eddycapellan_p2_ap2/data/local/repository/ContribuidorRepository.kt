package edu.ucne.eddycapellan_p2_ap2.data.local.repository

import edu.ucne.eddycapellan_p2_ap2.remote.DataSource
import edu.ucne.eddycapellan_p2_ap2.remote.Resource
import edu.ucne.eddycapellan_p2_ap2.remote.dto.ContribuidoreDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContribuidorRepository @Inject constructor (
    private val dataSource: DataSource
){
    fun getContributors(owner: String, repo: String): Flow<Resource<List<ContribuidoreDto>>> = flow {
        try {
            emit(Resource.Loading())
            val contributors = dataSource.getContributors(owner, repo)
            emit(Resource.Success(contributors))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
}