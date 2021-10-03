package com.mchew.atrestaurants.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mchew.atrestaurants.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.mchew.atrestaurants.databinding.FragmentHeaderBinding as VB

@AndroidEntryPoint
class HeaderFragment : BaseFragment<VB>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB = VB::inflate


}