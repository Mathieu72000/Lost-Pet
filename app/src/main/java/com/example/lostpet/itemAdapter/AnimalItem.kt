package com.example.lostpet.itemAdapter

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lostpet.Constants
import com.example.lostpet.FormDescriptionActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentMainItemBinding
import com.example.lostpet.room.model.Animal
import com.example.lostpet.room.model.AnimalCrossRef
import com.example.lostpet.viewmodel.AnimalItemViewModel
import com.xwray.groupie.databinding.BindableItem

class AnimalItem(private val item: AnimalItemViewModel) :
    BindableItem<FragmentMainItemBinding>(item.animalCrossRef.animal.animalId) {

    override fun getLayout() =
        R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {
        viewBinding.item = item

        viewBinding.root.setOnClickListener {
            it.context.startActivity(Intent(it.context, FormDescriptionActivity::class.java).apply {
                putExtra(Constants.ANIMAL_ID, item.animalCrossRef.animal.animalId)
            })
        }
    }
}
