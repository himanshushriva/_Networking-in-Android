package com.example.networkingdemobybrajesh
//https://www.youtube.com/watch?v=ykAZ856EVI4

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.networkingdemobybrajesh.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

const val TAG = "MainActivity"

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

        getRandomJoke()

        binding.btGetJoke.setOnClickListener {
            getRandomJoke()
        }
    }

    private fun getRandomJoke() {
        val service =
            RetrofitHelper.createRetrofitService(this@MainActivity, ApiService::class.java)

        //Starting a coroutine on the main thread, because Retrofit manages threading internally for suspend functions
        // “Retrofit executes suspend network calls off the main thread and resumes in the caller’s coroutine context.”
        lifecycleScope.launch {
            //Log.d(TAG, "${Thread.currentThread().name} thread")

            val response = service.getRandomJoke(RetrofitHelper.BASE_URL + "jokes/random")      //here, if we don't use "RetrofitHelper.BASE_URL +" still it'll work because we already provided BaseURL in Retrofit Builder. But How is both version working?
            //val response = service.getANewJoke()                                                                    //I think, Retrofit has something to find for BASE_URL in sent url(RetrofitHelper.BASE_URL) from here to @Url in getRandomJoke() method in ApiService and remove it from getting it doubled.

            //setting the value from response body on the TextView
            binding.tvJoke.text = response.body()?.value

            /*Log.d(TAG, "$response")
            Log.d(TAG, "${response.body()}")
            Log.d(TAG, "${response.body()?.value}")*/

            /*Log.d("MainActivity", "${Thread.currentThread().name}")
            Log.d("MainActivity", "${Thread.currentThread().state}")
            Log.d("MainActivity", "${Thread.currentThread().threadGroup}")*/
        }
    }
}