package com.example.lostpet.itemAdapter

import android.view.View
import com.example.lostpet.R
import com.example.lostpet.databinding.PictureItemBinding
import com.example.lostpet.viewmodel.PictureViewModel
import com.xwray.groupie.viewbinding.BindableItem

class PictureItem(private val item: PictureViewModel) :
    BindableItem<PictureItemBinding>() {

    override fun getLayout() =
        R.layout.picture_item


    override fun bind(viewBinding: PictureItemBinding, position: Int) {
        viewBinding.item = item
    }

    override fun initializeViewBinding(view: View): PictureItemBinding {
        return PictureItemBinding.bind(view)
    }


}