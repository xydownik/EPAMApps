package com.example.githubclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RepoViewModel by viewModels()
    private val adapter = RepoAdapter {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("ORG", it.owner.login)
            putExtra("REPO", it.name)
        }


        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val org = binding.editOrg.text.toString().trim()
            if (org.isNotEmpty()) viewModel.loadRepos(org)
        }

        viewModel.repos.observe(this) {
            adapter.submitList(it)
        }

        viewModel.loading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.message.observe(this) {
            it?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }
}
