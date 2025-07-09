package edu.ucne.eddycapellan_p2_ap2.remote

import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val api: GitHubApi
){
    suspend fun listRepos(username: String): List<RepositoryDto> {
        return api.listRepos(username)
    }

    suspend fun createRepository(repositoryDto: RepositoryDto): RepositoryDto {
        return api.createRepository(repositoryDto)
    }
}