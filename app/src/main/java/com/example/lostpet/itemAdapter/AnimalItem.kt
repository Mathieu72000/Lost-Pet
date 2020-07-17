package com.example.lostpet.itemAdapter

import android.content.Intent
import android.view.View
import com.example.lostpet.Constants
import com.example.lostpet.FormDescriptionActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentMainItemBinding
import com.example.lostpet.model.Animal
import com.example.lostpet.viewmodel.AnimalItemViewModel
import com.xwray.groupie.viewbinding.BindableItem

class AnimalItem(private val item: AnimalItemViewModel) :
    BindableItem<FragmentMainItemBinding>() {

    override fun getLayout() =
        R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {
        viewBinding.item = item

        viewBinding.root.setOnClickListener {
            it.context.startActivity(Intent(it.context, FormDescriptionActivity::class.java).apply {
                putExtra(
                    Constants.ANIMAL_ID,
                    item.animalCrossRef.animalId
                )
            })
        }
    }

    override fun initializeViewBinding(view: View): FragmentMainItemBinding {
        return FragmentMainItemBinding.bind(view)
    }
}
