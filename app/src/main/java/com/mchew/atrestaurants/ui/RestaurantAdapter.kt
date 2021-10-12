package com.mchew.atrestaurants.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mchew.atrestaurants.R
import com.mchew.atrestaurants.core.toPriceString
import com.mchew.atrestaurants.core.toRaitingCountString
import com.mchew.atrestaurants.databinding.ItemRestaurantBinding
import com.mchew.atrestaurants.di.getAdapterImageManager
import com.mchew.atrestaurants.model.domain.Restaurant

class RestaurantAdapter(
    private val context: Context,
    private val items: List<Restaurant>,
    private val onFavoriteClickListener: OnFavoriteClickListener,
    private val clickListener: (Restaurant) -> Any
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private val imageManager = context.getAdapterImageManager()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding, clickListener, onFavoriteClickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding,
        private val clickListener: (Restaurant) -> Any,
        private val onFavoriteClickListener: OnFavoriteClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.invoke(items[bindingAdapterPosition])
        }

        fun bind(restaurant: Restaurant) = with(binding) {
            name.text = restaurant.name
            rating.rating = restaurant.rating ?: 0f
            ratingCount.text = restaurant.ratingCount.toRaitingCountString()
            priceLevel.text = restaurant.priceLevel?.toPriceString()
            separator.isVisible = restaurant.priceLevel != null
            address.text = restaurant.formattedAddress
            //FIXME using placeholder image until fetching real image is ready
            imageManager.loadImage("https://picsum.photos/501/501", thumbnail)
            setFavoriteIcon(restaurant.isFavorite)
            favoriteIcon.setOnClickListener {
                restaurant.isFavorite = !restaurant.isFavorite
                setFavoriteIcon(restaurant.isFavorite)
                onFavoriteClickListener.onFavoriteClick(restaurant, restaurant.isFavorite)
            }
        }

        private fun setFavoriteIcon(isFavorite: Boolean) {
            binding.favoriteIcon.setImageDrawable(AppCompatResources.getDrawable(
                context,
                if (isFavorite)
                    R.drawable.ic_baseline_favorite_24
                else
                    R.drawable.ic_baseline_favorite_border_24
            ))
        }
    }
}

fun interface OnFavoriteClickListener{
    fun onFavoriteClick(restaurant: Restaurant, isFavorite: Boolean)
}