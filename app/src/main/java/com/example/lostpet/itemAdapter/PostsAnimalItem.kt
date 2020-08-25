package com.example.lostpet.itemAdapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.example.lostpet.Constants
import com.example.lostpet.FormActivity
import com.example.lostpet.FormDescriptionActivity
import com.example.lostpet.R
import com.example.lostpet.databinding.FragmentMainItemBinding
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
                        DialogInterface.BUTTON_NEGATIVE -> ctx.startActivity(Intent(ctx, FormActivity::class.java).apply {
                            putExtra(Constants.ANIMAL_ID, item.animalCrossRef.animalId)
                        })
                        DialogInterface.BUTTON_NEUTRAL -> null
                    }
                }
//                MaterialAlertDialogBuilder(
//                    ctx,
//                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
//                ).apply {
//                    setMessage("Do you want to delete this post ?")
//                    setPositiveButton(R.string.yes, dialogClickListener)
//                    setNegativeButton(R.string.no, dialogClickListener)
//                    create().show()
//                }
                MaterialAlertDialogBuilder(
                    ctx,
                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                ).apply {
                    setMessage("Pick an action")
                    setPositiveButton("Delete", dialogClickListener)
                    setNegativeButton("Modify", dialogClickListener)
                    setNeutralButton("Cancel", dialogClickListener)
                    create().show()
                }
                it.showContextMenu()
            }
        }

        if (item.animalCrossRef.pictureList?.isNotEmpty() == true) {
            Glide.with(viewBinding.root.context).load(item.animalCrossRef.pictureList[0])
                .into(viewBinding.animalImageviewCardview)
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