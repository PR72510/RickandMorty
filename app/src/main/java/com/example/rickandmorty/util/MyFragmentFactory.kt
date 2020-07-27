package com.example.rickandmorty.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.rickandmorty.ui.characterdetail.CharacterDetailFragment
import com.example.rickandmorty.ui.characters.CharactersFragment
import javax.inject.Inject

/**
 * Created by PR72510 on 26/7/20.
 */
class MyFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            CharactersFragment::class.java.name -> {
                CharactersFragment()
            }
            CharacterDetailFragment::class.java.name -> {
                CharacterDetailFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}