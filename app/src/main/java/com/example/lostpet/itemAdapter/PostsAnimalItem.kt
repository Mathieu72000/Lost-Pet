package com.example.lostpet.itemAdapter

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import coil.load
import com.example.lostpet.FormActivity
import com.example.lostpet.FormDescriptionActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentMainItemBinding
import com.example.lostpet.utils.Constants
import com.example.lostpet.viewmodel.AnimalItemViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xwray.groupie.viewbinding.BindableItem

class PostsAnimalItem(private val item: AnimalItemViewModel) :
    BindableItem<FragmentMainItemBinding>() {

    override fun getLayout() =
        R.layout.fragment_main_item

    override fun bind(viewBinding: FragmentMainItemBinding, position: Int) {
        viewBinding.item = item

        val broadcast: Intent = Intent().apply {
            action = Constants.DELETE_ITEM
            putExtra(Constants.ITEM_ID, item.animalCrossRef.animalId)
        }
        viewBinding.root.context?.let { ctx ->
            viewBinding.root.setOnLongClickListener {
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> ctx.sendBroadcast(broadcast)
                        DialogInterface.BUTTON_NEGATIVE -> ctx.startActivity(
                            Intent(
                                ctx,
                                FormActivity::class.java
                            ).apply {
                                putExtra(Constants.ANIMAL_ID, item.animalCrossRef.animalId)
                            })
                        DialogInterface.BUTTON_NEUTRAL -> null
                    }
                }
                MaterialAlertDialogBuilder(
                    ctx,
                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                ).apply {
                    setMessage(context.getString(R.string.pick_action))
                    setPositiveButton(context.getString(R.string.delete), dialogClickListener)
                    setNegativeButton(context.getString(R.string.modify), dialogClickListener)
                    setNeutralButton(context.getString(R.string.cancel), dialogClickListener)
                    create().show()
                }
                it.showContextMenu()
            }
        }

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