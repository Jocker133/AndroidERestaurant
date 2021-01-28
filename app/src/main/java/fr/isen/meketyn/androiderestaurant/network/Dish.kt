package fr.isen.meketyn.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Dish(
        @SerializedName("name_fr") val name: String,
        val ingredient: List<Ingredient>,
        val images: List<String>,
        val prices: List<Price>
): Serializable {

}