package com.example.lostpet.itemAdapter

import android.view.View
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentLostItemBinding
import com.example.lostpet.viewmodel.AnimalItemViewModel
import com.xwray.groupie.viewbinding.BindableItem

class LostAnimalItem(private val item: AnimalItemViewModel) :
    BindableItem<FragmentLostItemBinding>() {

    override fun getLayout() = R.layout.fragment_lost_item

    override fun bind(viewBinding: FragmentLostItemBinding, position: Int) {
        viewBinding.item = item
        viewBinding.executePendingBindings()
    }

    override fun initializeViewBinding(view: View): FragmentLostItemBinding {
        return FragmentLostItemBinding.bind(view)
    }

}