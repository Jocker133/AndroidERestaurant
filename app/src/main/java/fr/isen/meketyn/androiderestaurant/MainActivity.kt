package fr.isen.meketyn.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import fr.isen.meketyn.androiderestaurant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { findList(findViewById<Button>(R.id.button).text.toString()) }
        binding.button2.setOnClickListener { findList(findViewById<Button>(R.id.button2).text.toString()) }
        binding.button3.setOnClickListener { findList(findViewById<Button>(R.id.button3).text.toString()) }
    }
    private fun findList(message: String) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("message_key", message)
        startActivity(intent)
    }
}