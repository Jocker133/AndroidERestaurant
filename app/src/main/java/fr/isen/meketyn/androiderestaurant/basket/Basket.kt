package fr.isen.meketyn.androiderestaurant.basket

import fr.isen.meketyn.androiderestaurant.network.Dish
import java.io.Serializable

class Basket(val items: List<BasketItem>): Serializable {}

class BasketItem(val dish: Dish, val count: Int): Serializable {}