package fr.isen.meketyn.androiderestaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.meketyn.androiderestaurant.basket.Basket
import fr.isen.meketyn.androiderestaurant.basket.BasketItem
import fr.isen.meketyn.androiderestaurant.databinding.PanierCellBinding

class BasketAdapter(private val basket: Basket, private val context: Context, private val entryClickListener: (BasketItem) -> Unit): RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(binding: PanierCellBinding): RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.panierDishTitle
        val itemImage: ImageView = binding.panierDishImage
        val itemPrice: TextView = binding.panierDishPrice
        val itemQuantity: TextView = binding.panierDishQuantity
        val garbage: ImageView = binding.garbage

        fun bind(basket: BasketItem, context: Context, entryClickListener: (BasketItem) -> Unit) {
            itemTitle.text = basket.dish.name
            itemPrice.text = "${basket.dish.prices.first().price} €"
            Picasso.get().load(basket.dish.images.first()).into(itemImage)
            itemQuantity.text = basket.count.toString()
            garbage.setOnClickListener {
                entryClickListener.invoke(basket)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(PanierCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val panier = basket.items[position]
        holder.bind(panier, context, entryClickListener)
    }

    override fun getItemCount(): Int {
        return basket.items.count()
    }
}