package com.mchew.atrestaurants.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mchew.atrestaurants.R
import com.mchew.atrestaurants.core.ImageManager
import com.mchew.atrestaurants.core.getGooglePlacePhotoUrl
import com.mchew.atrestaurants.core.toPriceString
import com.mchew.atrestaurants.core.toRaitingCountString
import com.mchew.atrestaurants.databinding.DialogFragmentRestaurantDetailBinding
import com.mchew.atrestaurants.model.domain.Restaurant
import com.mchew.atrestaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantDetailDialogFragment : DialogFragment() {

    @Inject
    lateinit var imageManager: ImageManager

    private var _binding: DialogFragmentRestaurantDetailBinding? = null
    private val binding: DialogFragmentRestaurantDetailBinding
        get() = _binding!!

    private val viewModel: RestaurantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DialogFragmentRestaurantDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTheme() = R.style.ATBottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), R.style.ATBottomSheetDialog)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val restaurant = arguments?.getSerializable(ARGUMENT_RESTAURANT) as Restaurant
        with(binding) {
            name.text = restaurant.name
            rating.rating = restaurant.rating ?: 0f
            ratingCount.text = restaurant.ratingCount.toRaitingCountString()
            priceLevel.text = restaurant.priceLevel?.toPriceString()
            separator.isVisible = restaurant.priceLevel != null
            address.text = restaurant.address
            restaurant.photoReference?.let {
                imageManager.loadImage(getGooglePlacePhotoUrl(it, 1000), photo)
            } ?: run {
                photo.isGone = true
            }
            setOpenStatus(restaurant.isOpenNow)
            setFavoriteIcon(restaurant.isFavorite)
            favoriteIcon.setOnClickListener {
                restaurant.isFavorite = !restaurant.isFavorite
                setFavoriteIcon(restaurant.isFavorite)
                viewModel.setFavoriteStatus(restaurant, restaurant.isFavorite)
            }
        }
    }

    private fun setOpenStatus(isOpenNow: Boolean) {
        binding.openStatus.apply {
            if (isOpenNow) {
                text = requireContext().getString(R.string.restaurant_detail_open)
                setTextColor(resources.getColor(R.color.text_green))
            } else {
                text = requireContext().getString(R.string.restaurant_detail_closed)
                setTextColor(resources.getColor(R.color.text_red))
            }
        }
    }
    private fun setFavoriteIcon(isFavorite: Boolean) {
        binding.favoriteIcon.setImageDrawable(
            AppCompatResources.getDrawable(
            requireContext(),
            if (isFavorite)
                R.drawable.ic_baseline_favorite_24
            else
                R.drawable.ic_baseline_favorite_border_24
        ))
    }

    companion object {
        private const val ARGUMENT_RESTAURANT = "restaurant"

        fun navigate(
            fragmentManager: FragmentManager,
            restaurant: Restaurant,
        ) = RestaurantDetailDialogFragment().run {
            arguments = Bundle().apply {
                putSerializable(ARGUMENT_RESTAURANT, restaurant)
            }
            show(fragmentManager, "restaurant_detail")
        }
    }
}