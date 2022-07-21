package com.bchmsl.presentation_upd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bchmsl.presentation_upd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val usersAdapter by lazy { UsersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onRestart() {
        super.onRestart()
        usersAdapter.submitList(usersList.toList())
    }

    private fun init() {
        setupRecycler()
        listeners()
    }

    private fun setupRecycler() {
        binding.rvRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        usersAdapter.submitList(usersList)
    }

    private fun listeners() {
        binding.fbtnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            startActivity(intent)
        }
        usersAdapter.itemClickListener = { user ->
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            intent.putExtra("userId", user.id)
            startActivity(intent)
        }
        usersAdapter.removeClickListener = { user ->
            removeUser(user)
        }
        binding.srLayout.setOnRefreshListener {
            setupRecycler()
            binding.srLayout.isRefreshing = false
        }
    }

    private fun removeUser(user : User) {
        usersList.remove(user)
        usersAdapter.submitList(usersList.toList())
    }
}