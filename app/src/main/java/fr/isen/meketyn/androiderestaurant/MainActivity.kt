package fr.isen.meketyn.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            findList()
        }
    }
    fun findList() {
        val intent = Intent(this, ListActivity::class.java)
        val message = findViewById<Button>(R.id.button).text.toString()
        intent.putExtra("message_key", message)
        startActivity(intent)
    }
}