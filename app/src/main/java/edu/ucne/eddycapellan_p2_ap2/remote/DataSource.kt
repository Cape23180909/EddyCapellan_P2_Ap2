package edu.ucne.eddycapellan_p2_ap2.remote

import edu.ucne.eddycapellan_p2_ap2.remote.dto.ContribuidoreDto
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val api: GitHubApi
){
    suspend fun listRepos(): List<RepositoryDto> {
        val response = api.listRepos()
        if(response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    suspend fun getContributors(owner: String, repo: String): List<ContribuidoreDto> {
        val response = api.getContributors(owner, repo)
        if(response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }
}