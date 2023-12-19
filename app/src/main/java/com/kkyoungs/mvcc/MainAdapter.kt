package com.kkyoungs.mvcc

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkyoungs.mvcc.databinding.ItemNewsBinding

class MainAdapter(val context: Context, val newsList : ArrayList<NewsData>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var binding: ItemNewsBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!.root)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news  = newsList[position]
        binding!!.title.text = news.title
        val description = news.description
        if (!description.equals(null)){
            binding!!.description.text =description
        }else{
            binding!!.description.text = "-"
        }

        val uri = Uri.parse(news.urlToImage)
        binding!!.image.setImageURI(uri)
        binding!!.root.tag = position
    }
    fun getNews(position: Int): NewsData? {
        return if (newsList!=null){
            newsList.get(position)
        }else{
            null
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    }


