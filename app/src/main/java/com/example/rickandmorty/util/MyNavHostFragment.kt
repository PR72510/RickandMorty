package com.example.rickandmorty.util

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by PR72510 on 26/7/20.
 */

@AndroidEntryPoint
class MyNavHostFragment: NavHostFragment() {

    @Inject
    lateinit var fragmentFactory: MyFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = fragmentFactory
    }
}