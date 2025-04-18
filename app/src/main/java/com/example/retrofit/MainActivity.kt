package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retService : AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        getRequestWithQueryParameter()

        getRequestWithPathParameter()

    }

    // Query Parameter Example
    private fun getRequestWithQueryParameter() {
        val responseLiveData : LiveData<Response<Album>> = liveData {

//            val response = retService.getAlbums() // Getting all the data

            val response = retService.getSortedAlbums(3)    // Using Query Parameters
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if(albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumItem = albumsList.next()

                    // Displaying all the title data
                    Log.i("MYTAG", "Title : ${albumItem.title}")

                    //Using Query Parameters
                    val result = " " + "Album Id : ${albumItem.id}" + "\n" +
                            " " + "Album User Id : ${albumItem.userId}" + "\n" +
                            " " + "Album Title : ${albumItem.title}" + "\n\n\n"

                    findViewById<TextView>(R.id.text_view).append(result)
                }
            }
        })
    }

    // Path Parameter Example
    private fun getRequestWithPathParameter() {
        val responsePathLiveData : LiveData<Response<AlbumItem>> = liveData {
            val response = retService.getAlbumById(3)
            emit(response)
        }

        responsePathLiveData.observe(this, Observer {
            val title = it.body()?.title
            if(title != null) {
                Toast.makeText(applicationContext, title, Toast.LENGTH_SHORT).show()
            }
        })
    }
}