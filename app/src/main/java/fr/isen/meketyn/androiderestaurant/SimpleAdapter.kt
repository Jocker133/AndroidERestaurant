package fr.isen.meketyn.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.meketyn.androiderestaurant.databinding.ItemSimpleItemviewBinding

class SimpleAdapter(private val entries: List<String>) : RecyclerView.Adapter<SimpleAdapter.DishesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        return DishesViewHolder(ItemSimpleItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        holder.titleView.text = entries[position]
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class DishesViewHolder(dishesBinding: ItemSimpleItemviewBinding): RecyclerView.ViewHolder(dishesBinding.root) {
        val titleView: TextView = dishesBinding.simpleText
    }
}