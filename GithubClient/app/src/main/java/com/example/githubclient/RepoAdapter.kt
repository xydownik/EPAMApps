package com.example.githubclient

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemRepoBinding
import com.example.githubclient.models.Repo

class RepoAdapter(val onClick: (Repo) -> Unit) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val items = mutableListOf<Repo>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<Repo>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class RepoViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = items[position]
        with(holder.binding) {
            textRepoName.text = repo.name
            textRepoDesc.text = repo.description ?: "No description message"
            textRepoDesc.maxLines = 3
            textRepoDesc.ellipsize = TextUtils.TruncateAt.END
            root.setOnClickListener { onClick(repo) }
        }
    }

    override fun getItemCount() = items.size
}
