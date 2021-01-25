package fr.isen.meketyn.androiderestaurant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val message = intent.getStringExtra("message_key")
        val messageTextView : TextView = findViewById(R.id.textView2)
        messageTextView.text = message
    }
}