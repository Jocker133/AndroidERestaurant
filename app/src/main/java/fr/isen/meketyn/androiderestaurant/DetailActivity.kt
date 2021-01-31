package fr.isen.meketyn.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.meketyn.androiderestaurant.basket.BasketItem
import fr.isen.meketyn.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.meketyn.androiderestaurant.network.Dish
import java.lang.Integer.max

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var itemCount = 1
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dish = intent.getSerializableExtra("dish") as Dish
        dish?.let {
            displayDish(it)
        }
    }

    private fun displayDish(dish: Dish) {
        val dishName = binding.dishName
        dishName.text = dish.name
        val dishIngredients = binding.dishIngredients
        if(dish.ingredients != null)
            dishIngredients.text = dish.ingredients.map {
                it.name
            }.joinToString(",")
        else {
            dishIngredients.text = dish.images[0]
        }
        viewPager = binding.dishImages
        val pageAdapter = PageAdapter(this, dish.images)
        viewPager.adapter = pageAdapter

        binding.less.setOnClickListener {
            itemCount = max(1, itemCount - 1)
            refreshShop(dish)
        }

        binding.more.setOnClickListener {
            itemCount+=1
            refreshShop(dish)
        }
    }

    private fun refreshShop(dish: Dish) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${"Total"} $price€"
        addtoBasket(dish, itemCount)
    }

    private fun addtoBasket(dish: Dish, count: Int) {
        val item = BasketItem(dish, count)
        val json = GsonBuilder().create().toJson(item)
        Log.d("basket", json)
    }
}