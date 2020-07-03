package com.example.lostpet.itemAdapter

import android.view.View
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentMainItemBinding
import com.example.lostpet.viewmodel.AnimalItemViewModel
import com.xwray.groupie.viewbinding.BindableItem

class AnimalItem(private val item: AnimalItemViewModel) :
    BindableItem<FragmentMainItemBinding>(item.animalCrossRef.animal.animalId) {

    override fun getLayout() =
        R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {

    }

    override fun initializeViewBinding(view: View): FragmentMainItemBinding {
        return FragmentMainItemBinding.bind(view)
    }
}
