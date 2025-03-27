package com.example.githubclient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.databinding.ActivityDetailsBinding
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val org = intent.getStringExtra("ORG")!!
        val repo = intent.getStringExtra("REPO")!!

        lifecycleScope.launch {
            binding.progressBarDetails.visibility = View.VISIBLE
            try {
                val details = RetrofitClient.api.getRepoDetails(org, repo)
                binding.textRepoName.text = details.name
                binding.textRepoDesc.text = details.description ?: "No description message"
                binding.textForks.text = "Forks: ${details.forks_count}"
                binding.textWatchers.text = "Watchers: ${details.watchers_count}"
                binding.textIssues.text = "Open Issues: ${details.open_issues_count}"
                if (details.fork && details.parent != null) {
                    binding.textParent.text = "Forked from: ${details.parent.full_name}"
                    binding.textParent.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                Toast.makeText(this@DetailsActivity, "Failed to load details", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBarDetails.visibility = View.GONE
            }
        }
    }
}
