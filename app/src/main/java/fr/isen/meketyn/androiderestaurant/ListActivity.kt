package fr.isen.meketyn.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.meketyn.androiderestaurant.databinding.ActivityListBinding
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
        loadList()
    }

    private fun loadList() {
        var entries = listOf<String>("salade", "boeuf", "glace")
        val adapter = SimpleAdapter(entries)
        binding.simpleRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.simpleRecyclerview.adapter = adapter
    }
}