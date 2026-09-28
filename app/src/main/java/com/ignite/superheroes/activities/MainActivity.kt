package com.ignite.superheroes.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ignite.superheroes.R
import com.ignite.superheroes.adapters.SuperheroAdapter
import com.ignite.superheroes.data.Superhero
import com.ignite.superheroes.data.SuperheroApiService
import com.ignite.superheroes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: SuperheroAdapter

    lateinit var superheroList: List<Superhero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        superheroList = emptyList()

        adapter = SuperheroAdapter(superheroList) { position ->
            navigateToDetail(superheroList[position])
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchByName("a")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    searchByName("a")
                }
                return true
            }
        })

        return true
    }

    private fun navigateToDetail(superhero: Superhero) {
        //Toast.makeText(this, superhero.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_SUPERHERO_ID, superhero.id)
        startActivity(intent)
    }

    private fun searchByName(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperheroApiService::class.java)
                val result = apiService.findSuperheroesByName(query)

                runOnUiThread {
                    superheroList = result.results ?: emptyList()
                    adapter.updateData(superheroList)
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        /*val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/

        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7252591128153666/")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}