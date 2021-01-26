package fr.isen.meketyn.androiderestaurant

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
        binding.button4.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val message = intent.getStringExtra("message_key")
        val messageTextView : TextView = findViewById(R.id.textView2)
        messageTextView.text = message

        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val jsonData = JSONObject()
        jsonData.put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
        Response.Listener<JSONObject> { response ->
            Log.d("request", response.toString(2))
            val menuResult = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
            /*menuResult.data.forEach {
                Log.d("request", it.name)
            }*/
            val items = menuResult.data.firstOrNull{
                it.name == message
            }
            loadList(items?.items)
        },
        Response.ErrorListener { error ->
            error.message?.let {
                Log.d("request", it)
            } ?: run {
                Log.d("request", error.toString())
            }})

        queue.add(jsonObjectRequest)
        //loadList()
    }

    private fun loadList(list: List<Dish>?) {
        //var entries = listOf<String>("salade", "boeuf", "glace")
        val entries = list?.map { it.name }
        list?.let {
            val adapter = SimpleAdapter(it)
            binding.simpleRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.simpleRecyclerview.adapter = adapter
        }

    }
}
