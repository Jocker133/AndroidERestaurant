package fr.isen.meketyn.androiderestaurant.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.meketyn.androiderestaurant.databinding.ActivityListCommandBinding
import fr.isen.meketyn.androiderestaurant.network.NetworkConstant
import org.json.JSONObject

class ListCommandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListCommandBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCommandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idUser = intent.getStringExtra("id_user")

        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.LIST_ORDERS

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_USER, idUser)
        jsonData.put(NetworkConstant.ID_SHOP, "1")

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                Log.d("request", response.toString(2))
                displayList(response.toString())
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                }
            }
        )
        queue.add(request)
    }

    private fun displayList(response: String) {
        val listCommand = GsonBuilder().create().fromJson(response, Liste::class.java)
        val items = listCommand.data
        loadList(items)
    }

    private fun loadList(list: List<Command>) {
        var message = "Vos commandes : \n"
        var temp = ""
        binding.sender.text = "Vous :  ${list[0].sender}"
        binding.receiver.text = "Ceux qui vous ont donné vos commandes :  ${list[0].receiver}"
        for (i in list.indices) {
            temp += "- ${list[i].message} \n"
        }
        message = "$message $temp"
        binding.products.text = message
    }
}