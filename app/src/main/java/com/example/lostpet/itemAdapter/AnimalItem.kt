package com.example.lostpet.itemAdapter

import android.content.Intent
import android.view.View
import coil.load
import com.example.lostpet.Constants
import com.example.lostpet.FormDescriptionActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentMainItemBinding
import com.example.lostpet.viewmodel.AnimalItemViewModel
import com.xwray.groupie.viewbinding.BindableItem

class AnimalItem(private val item: AnimalItemViewModel) :
    BindableItem<FragmentMainItemBinding>(item.animalCrossRef.animalId.hashCode().toLong()) {

    override fun getLayout() =
        R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {
        viewBinding.item = item
        if (item.animalCrossRef.pictureList?.isNotEmpty() == true) {
            viewBinding.animalImageviewCardview.load(item.animalCrossRef.pictureList[0])
        }
        viewBinding.root.setOnClickListener {
            it.context.startActivity(Intent(it.context, FormDescriptionActivity::class.java).apply {
                putExtra(
                    Constants.ANIMAL_ID,
                    item.animalCrossRef.animalId
                )
            })
        }

        viewBinding.executePendingBindings()
    }

    override fun initializeViewBinding(view: View): FragmentMainItemBinding {
        return FragmentMainItemBinding.bind(view)
    }
}
