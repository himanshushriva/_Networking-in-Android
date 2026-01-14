package com.example.retrofitbyphilipp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitbyphilipp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

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

        setupRecyclerView()

        //My simple version for this
        /*val api = RetrofitInstance.api
        //Starting a coroutine on the main thread
        lifecycleScope.launchWhenCreated {
            val response = api.getTodos()
            val todoList = response.body()!!

            /*val todos =  todoList.forEach {
                it
            }*/

            todoAdapter = TodoAdapter(this@MainActivity, todoList)
            binding.rvTodoList.adapter = todoAdapter
            binding.rvTodoList.layoutManager = LinearLayoutManager(this@MainActivity)
        }*/

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                todoAdapter.todos = response.body()!!
            } else {
                Log.d(TAG, "Response not successful")
            }

            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvTodoList.apply {
        todoAdapter = TodoAdapter()
        Log.d(TAG, "Total item = ${todoAdapter/*.onCreateViewHolder()*/.itemCount}")       //Try to call TodoAdapter.getItemCount() -> because overridden function can not be set as private because how can then android os call it internally.
        binding.rvTodoList.adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}