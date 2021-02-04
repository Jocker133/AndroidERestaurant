package fr.isen.meketyn.androiderestaurant

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.meketyn.androiderestaurant.basket.Basket
import fr.isen.meketyn.androiderestaurant.basket.BasketItem
import fr.isen.meketyn.androiderestaurant.databinding.ActivityPanierBinding
import fr.isen.meketyn.androiderestaurant.list.ListCommandActivity
import fr.isen.meketyn.androiderestaurant.network.NetworkConstant
import fr.isen.meketyn.androiderestaurant.registration.RegisterActivity
import fr.isen.meketyn.androiderestaurant.utils.Loader
import org.json.JSONObject

class PanierActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanierBinding
    private lateinit var basket: Basket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        basket = Basket.getBasket(this)
        reloadData(basket)
        binding.orderButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, RegisterActivity.REQUEST_CODE)
        }
        displayButton()
    }

    private fun reloadData(basket: Basket) {
        binding.panierView.layoutManager = LinearLayoutManager(this)
        binding.panierView.adapter = BasketAdapter(basket, this) {
            val basket = Basket.getBasket(this)
            val itemToDelete = basket.items.firstOrNull { it.dish.name == it.dish.name }
            basket.items.remove(itemToDelete)
            basket.save(this)
            reloadData(basket)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisterActivity.REQUEST_CODE) {
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if(idUser != -1)
                sendOrder(idUser)
        }
    }

    private fun displayButton() {
        val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
        binding.commandList.isVisible = idUser != -1
        if(binding.commandList.isVisible) {
            binding.commandList.setOnClickListener {
                val intent = Intent(this, ListCommandActivity::class.java)
                intent.putExtra("id_user", idUser.toString())
                startActivity(intent)
            }
        }
    }

    private fun sendOrder(idUser: Int) {
        val message = basket.items.map { "${it.count}x ${it.dish.name}" }.joinToString("\n")

        var loader = Loader()
        loader.show(this, "Traitement de votre commande")

        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_ORDER

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")
        jsonData.put(NetworkConstant.ID_USER, idUser)
        jsonData.put(NetworkConstant.MSG, message)

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                loader.hide(this)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Votre commande a bien été prise en compte")
                builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                    basket.clear()
                    basket.save(this)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                builder.show()
            },
            { error ->
                loader.hide(this)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Votre commande est en échec")
                builder.setPositiveButton("OK") { dialogInterface: DialogInterface?, i: Int ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                    Log.d("request", String(error.networkResponse.data))
                }
            }
        )
        queue.add(request)
    }
}