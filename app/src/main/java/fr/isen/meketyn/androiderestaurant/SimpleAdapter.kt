package fr.isen.meketyn.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.meketyn.androiderestaurant.databinding.ItemSimpleItemviewBinding
import fr.isen.meketyn.androiderestaurant.network.Dish

class SimpleAdapter(private val entries: List<Dish>, private val entryClickListener: (Dish) -> Unit) : RecyclerView.Adapter<SimpleAdapter.DishesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        return DishesViewHolder(ItemSimpleItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        val dish = entries[position]
        holder.layout.setOnClickListener {
            entryClickListener.invoke(dish)
        }
        holder.bind(dish)
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class DishesViewHolder(dishesBinding: ItemSimpleItemviewBinding): RecyclerView.ViewHolder(dishesBinding.root) {
        val titleView: TextView = dishesBinding.simpleText
        val dishPrice: TextView = dishesBinding.dishprice
        val dishImage: ImageView = dishesBinding.dishImage
        val layout = dishesBinding.root

        fun bind(dish: Dish) {
            titleView.text = dish.name
            dishPrice.text = "${dish.prices.first().price} €"
            val picasso = Picasso.get()
            if(dish.images.first() != "")
                picasso.load(dish.images.first()).into(dishImage)
        }
    }
}