package fr.isen.meketyn.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.meketyn.androiderestaurant.databinding.ActivityListBinding
import fr.isen.meketyn.androiderestaurant.network.Dish
import fr.isen.meketyn.androiderestaurant.network.MenuResult
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("message_key")

        binding.swipeLayout.setOnRefreshListener {
            resetCache()
            makeRequest(message)
        }

        val messageTextView : TextView = findViewById(R.id.textView2)
        messageTextView.text = message
        loadList(listOf<Dish>())

        makeRequest(message)

        /*resultFromCache()?.let {
            //La requete est en cache
            parseResult(it, message)
        } ?: run {
            //La requete n'est pas en cache
            val queue = Volley.newRequestQueue(this)
            val url = "http://test.api.catering.bluecodegames.com/menu"

            val jsonData = JSONObject()
            jsonData.put("id_shop", "1")
            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonData,
                    Response.Listener<JSONObject> { response ->
                        cacheResult(response.toString())
                        parseResult(response.toString(), message)
                    },
                    Response.ErrorListener { error ->
                        error.message?.let {
                            Log.d("request", it)
                        } ?: run {
                            Log.d("request", error.toString())
                        }
                    })

            queue.add(jsonObjectRequest)
        }*/
    }

    private fun makeRequest(message: String?) {
        resultFromCache()?.let {
            //La requete est en cache
            parseResult(it, message)
        } ?: run {
            //La requete n'est pas en cache
            val queue = Volley.newRequestQueue(this)
            val url = "http://test.api.catering.bluecodegames.com/menu"

            val jsonData = JSONObject()
            jsonData.put("id_shop", "1")
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                Response.Listener<JSONObject> { response ->
                    binding.swipeLayout.isRefreshing = false
                    cacheResult(response.toString())
                    parseResult(response.toString(), message)
                },
                Response.ErrorListener { error ->
                    binding.swipeLayout.isRefreshing = false
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                    }
                })

            queue.add(jsonObjectRequest)
        }
    }

    private fun cacheResult(response: String) {
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("REQUEST_CACHE", response)
        editor.apply()
    }

    private fun resetCache() {
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("REQUEST_CACHE")
        editor.apply()
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES_NAME", Context.MODE_PRIVATE)
        return sharedPreferences.getString("REQUEST_CACHE", null)
    }

    private fun parseResult(response: String, message: String?) {
        val menuResult = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
        val items = menuResult.data.firstOrNull{
            it.name == message
        }
        loadList(items?.items)
    }

    private fun loadList(list: List<Dish>?) {
        list?.let {
            val adapter = SimpleAdapter(it) {dish ->
                if(dish.ingredients == null)
                    Log.d("dish", "ingredient is null")
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("dish", dish)
                startActivity(intent)
            }
            binding.simpleRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.simpleRecyclerview.adapter = adapter
        }

    }
}
