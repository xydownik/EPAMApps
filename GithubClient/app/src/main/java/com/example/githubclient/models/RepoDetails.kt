package com.example.githubclient.models

data class RepoDetails(
    val name: String,
    val description: String?,
    val forks_count: Int,
    val watchers_count: Int,
    val open_issues_count: Int,
    val fork: Boolean,
    val parent: ParentRepo?
)