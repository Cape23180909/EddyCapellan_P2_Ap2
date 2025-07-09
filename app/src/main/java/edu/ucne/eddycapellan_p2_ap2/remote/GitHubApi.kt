package edu.ucne.eddycapellan_p2_ap2.remote

import edu.ucne.eddycapellan_p2_ap2.remote.dto.ContribuidoreDto
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/repos")
    suspend fun listRepos(@Path("username") username: String): List<RepositoryDto>

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<ContribuidoreDto>

    @POST("user/repos")
    suspend fun createRepository(@Body repo: RepositoryDto): RepositoryDto
}