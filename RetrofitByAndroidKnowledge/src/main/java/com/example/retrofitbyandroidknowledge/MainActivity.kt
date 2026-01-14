package com.example.retrofitbyandroidknowledge

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.retrofitbyandroidknowledge.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apiService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        /*lifecycleScope.launch {
            val response = apiService.getAlbum()
            Log.d("MainActivity", "${response}")
        }*/

        //Setting response data with LiveData to the TextView
        val responseLiveData: LiveData<Response<Albums/*List<AlbumItem>*/>> = liveData {    //Builds a LiveData that has values yielded from the given block that executes on a LiveDataScope.
            val response = apiService.getAlbum()
            /*this.*/emit(response)
        }

        responseLiveData.observe(this, {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    //Log.d("MainActivity", "${albumList.next().id}")
                    val albumItem = albumList.next()

                    val albumTitle = "Album Title: ${albumItem.title} \n"
                    binding.tvTitle.append(albumTitle)
                }
            }

            //This is my version (neat, short and simple)
            /*val albumList = it.body()
            Log.d("MainActivity", "$albumList")
            if (albumList != null) {
                for (item in albumList.iterator()) {    //iterator() = listIterator() here
                    val albumTitle = "Album Title: ${item.id} \n"
                    binding.tvTitle.append(albumTitle)      //We can also use a string builder for this
                    // binding.tvTitle.text = ...
                    //binding.tvTitle.value = ...
                }
            }*/
        })
    }
}