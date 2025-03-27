package com.example.githubclient

import com.example.githubclient.models.Repo
import com.example.githubclient.models.RepoDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("orgs/{org}/repos")
    suspend fun getRepos(@Path("org") org: String): List<Repo>

    @GET("repos/{org}/{repo}")
    suspend fun getRepoDetails(
        @Path("org") org: String,
        @Path("repo") repo: String
    ): RepoDetails
}
