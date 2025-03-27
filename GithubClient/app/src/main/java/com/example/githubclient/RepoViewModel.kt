package com.example.githubclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.models.Repo
import kotlinx.coroutines.launch

class RepoViewModel : ViewModel() {
    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>> = _repos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    fun loadRepos(org: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = RetrofitClient.api.getRepos(org)
                if (result.isNotEmpty()) {
                    _repos.value = result
                    _message.value = null
                } else {
                    _repos.value = emptyList()
                    _message.value = "No repos found for organization $org"
                }
            } catch (e: Exception) {
                _repos.value = emptyList()
                _message.value = "Failed to load repositories"
            } finally {
                _loading.value = false
            }
        }
    }
}
