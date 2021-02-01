package fr.isen.meketyn.androiderestaurant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.basket)?.actionView
        val countText = menuView?.findViewById(R.id.basketCount) as? TextView
        val count = getItemCount()
        countText?.isVisible = count > 0
        countText?.text = count.toString()

        menuView?.setOnClickListener {
            val intent = Intent(this, PanierActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    private fun getItemCount(): Int {
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES_NAME", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("ITEM_COUNT", 0)
    }
}