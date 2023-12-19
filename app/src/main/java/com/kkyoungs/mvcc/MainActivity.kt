package com.kkyoungs.mvcc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kkyoungs.mvcc.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var adapter : MainAdapter ?= null
    private var layoutManager : RecyclerView.LayoutManager ?= null
    var queue :RequestQueue ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //View 관련 메소드
        setContentView(mBinding.root)
        mBinding.newsRecycler.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        mBinding.newsRecycler.layoutManager = layoutManager
        queue = Volley.newRequestQueue(this)

    }

    private fun getNews(){
        val url ="https://newsapi.org/v2/top-headlines?country=kr&apiKey=35927dffdce64a759f8895b888c4a078"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String>{
                 try {
                     val jsonObj = JSONObject(it)
                     val arrayArticles = jsonObj.getJSONArray("articles")
                     val news = ArrayList<NewsData>()
                     for (i in 0..arrayArticles.length()-1){
                         val obj = arrayArticles.getJSONObject(i)
                         val title = obj.getString("title")
                         val description = obj.getString("description")
                         val img = obj.getString("urlToImage")
                         val author = obj.getString("author")
                         val url = obj.getString("url")
                         val publishedAt = obj.getString("publishedAt")
                         val content = obj.getString("content")

                         val newsData = NewsData(author, title, description, url, img, publishedAt,content)
                         news.add(newsData)
                     }
                     adapter = MainAdapter(this, news, View.OnClickListener {
                         val obj = adapter.tag
                         if (obj != null){
                             val position : Int = obj
                             val intent = Intent(this, NewsPageActivity::class.java)
                             intent.putExtra("news", adapter!!.getNews(position))

                         }

                     })
                 }   catch (e:JSONException){
                     e.printStackTrace()
                 }
            }
            , Response.ErrorListener{


        })
    }
}