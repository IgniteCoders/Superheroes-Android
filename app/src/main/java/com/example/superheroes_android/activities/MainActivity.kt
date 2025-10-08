package com.example.superheroes_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes_android.R
import com.example.superheroes_android.adapters.GameAdapter
import com.example.superheroes_android.data.Game
import com.example.superheroes_android.data.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    lateinit var adapter: GameAdapter

    var filteredGameList: List<Game> = emptyList()
    var originalGameList: List<Game> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)

        adapter = GameAdapter(filteredGameList) { position ->
            val superhero = filteredGameList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("SUPERHERO_ID", superhero.id)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        getGameList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filteredGameList = originalGameList.filter { it.title.contains(newText, true) }
                adapter.updateItems(filteredGameList)
                return true
            }
        })

        return true
    }

    fun getGameList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = GameService.getInstance()
                originalGameList = service.getAllGames()
                filteredGameList = originalGameList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(filteredGameList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}