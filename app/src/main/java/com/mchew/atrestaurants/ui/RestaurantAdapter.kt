package com.mchew.atrestaurants.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mchew.atrestaurants.databinding.ItemRestaurantBinding
import com.mchew.atrestaurants.di.getAdapterImageManager
import com.mchew.atrestaurants.model.domain.Restaurant
import java.lang.StringBuilder

class RestaurantAdapter(
    context: Context,
    private val items: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private val imageManager = context.getAdapterImageManager()

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
            priceLevel.text = restaurant.priceLevel?.toPriceString()
            //FIXME using placeholder image until fetching real image is ready
            imageManager.loadImage("https://picsum.photos/501/501", thumbnail)
        }

        private fun Int.toPriceString(): String {
            val sb = StringBuilder()
            for (i in 0 until this) {
                sb.append("$")
            }
            return sb.toString()
        }
    }
}