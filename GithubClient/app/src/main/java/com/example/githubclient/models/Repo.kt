package com.example.githubclient.models

data class Repo(
    val name: String,
    val description: String?,
    val fork: Boolean,
    val owner: Owner
)
