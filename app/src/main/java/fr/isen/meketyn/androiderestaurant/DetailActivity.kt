package fr.isen.meketyn.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.meketyn.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.meketyn.androiderestaurant.network.Dish

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dish = intent.getSerializableExtra("dish") as Dish
        displayDish(dish)
    }

    fun displayDish(dish: Dish) {
        val dishName = binding.dishName
        dishName.text = dish.name
        val dishIngredients = binding.dishIngredients
        if(dish.ingredient != null)
            dishIngredients.text = dish.ingredient.joinToString(",")
        else {
            dishIngredients.text = dish.images[0]
        }
    }
}