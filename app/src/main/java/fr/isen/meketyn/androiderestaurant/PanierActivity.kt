package fr.isen.meketyn.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.meketyn.androiderestaurant.basket.Basket
import fr.isen.meketyn.androiderestaurant.basket.BasketItem
import fr.isen.meketyn.androiderestaurant.databinding.ActivityPanierBinding

class PanierActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanierBinding
    private lateinit var basket: Basket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        basket = Basket.getBasket(this)
        reloadData(basket)
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

    /*private fun onDeleteItem(item: BasketItem) {
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull { it.dish.name == item.dish.name }
        basket.items.remove(itemToDelete)
        basket.save(this)
        reloadData(basket)
    }*/
}