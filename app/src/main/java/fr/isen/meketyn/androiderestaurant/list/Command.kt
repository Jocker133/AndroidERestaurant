package fr.isen.meketyn.androiderestaurant.list

import java.io.Serializable

class Command(
        val sender: String,
        val receiver: String,
        val message: String
): Serializable {}