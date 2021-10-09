package com.mchew.atrestaurants.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mchew.atrestaurants.databinding.ItemRestaurantBinding
import com.mchew.atrestaurants.model.domain.Restaurant
import java.lang.StringBuilder

class RestaurantAdapter(
    private val items: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) = with(binding) {
            name.text = restaurant.name
            rating.rating = restaurant.rating ?: 0f
            priceLevel.text = restaurant.priceLevel?.toPticeString()
        }

        private fun Int.toPticeString(): String {
            val sb = StringBuilder()
            for (i in 0 until this) {
                sb.append("$")
            }
            return sb.toString()
        }
    }
}