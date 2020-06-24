package com.example.lostpet.itemAdapter

import com.example.lostpet.R
import com.example.lostpet.databinding.PictureItemBinding
import com.example.lostpet.viewmodel.PictureViewModel
import com.xwray.groupie.databinding.BindableItem

class PictureItem(private val item: PictureViewModel) :
    BindableItem<PictureItemBinding>(System.identityHashCode(item.base64).toLong()) {

    override fun getLayout() =
        R.layout.picture_item


    override fun bind(viewBinding: PictureItemBinding, position: Int) {
        viewBinding.item = item
    }
}