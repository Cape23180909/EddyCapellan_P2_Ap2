package edu.ucne.eddycapellan_p2_ap2.remote

import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/repos")
    suspend fun listRepos(@Path("username") username: String): List<RepositoryDto>
}