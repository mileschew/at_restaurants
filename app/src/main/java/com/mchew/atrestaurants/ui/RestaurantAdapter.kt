package com.mchew.atrestaurants.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mchew.atrestaurants.core.toPriceString
import com.mchew.atrestaurants.core.toRaitingCountString
import com.mchew.atrestaurants.databinding.ItemRestaurantBinding
import com.mchew.atrestaurants.di.getAdapterImageManager
import com.mchew.atrestaurants.model.domain.Restaurant

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
            ratingCount.text = restaurant.ratingCount.toRaitingCountString()
            priceLevel.text = restaurant.priceLevel?.toPriceString()
            separator.isVisible = restaurant.priceLevel != null
            supportingText.text = restaurant.formattedAddress
            //FIXME using placeholder image until fetching real image is ready
            imageManager.loadImage("https://picsum.photos/501/501", thumbnail)
        }
    }
}