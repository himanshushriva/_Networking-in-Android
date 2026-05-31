package com.example.retrofitexample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val quotesAPI = RetrofitHelper.getInstance().create(QuotesAPI::class.java)

        GlobalScope.launch(/*Dispatchers.IO*/) {
            try {
                val result = quotesAPI.getQuotes(1)
                if (result.isSuccessful) {
                    Log.d("TAG", "onCreate: ${result.body().toString()}")

                    withContext(Dispatchers.Main) {
                        findViewById<TextView>(R.id.textView).text = result.body().toString()
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error occurred : ${e}")
                Log.e("API_ERROR", "Error occurred", e.cause)
            }
        }
    }
}