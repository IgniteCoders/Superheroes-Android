package com.example.freegames_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freegames_android.R
import com.example.freegames_android.adapters.GameAdapter
import com.example.freegames_android.data.Game
import com.example.freegames_android.data.GameService
import com.example.freegames_android.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: GameAdapter

    var filteredGameList: List<Game> = emptyList()
    var originalGameList: List<Game> = emptyList()

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

        adapter = GameAdapter(filteredGameList) { position ->
            val superhero = filteredGameList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_GAME_ID, superhero.id)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)

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