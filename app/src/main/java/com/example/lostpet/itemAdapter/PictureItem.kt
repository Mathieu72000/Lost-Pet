package com.example.lostpet.itemAdapter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.View
import coil.api.load
import com.bumptech.glide.Glide
import com.example.lostpet.Constants
import com.example.lostpet.R
import com.example.lostpet.databinding.PictureItemBinding
import com.example.lostpet.viewmodel.PictureViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xwray.groupie.viewbinding.BindableItem

class PictureItem(private val item: PictureViewModel) :
    BindableItem<PictureItemBinding>() {

    override fun getLayout() =
        R.layout.picture_item


    @SuppressLint("PrivateResource")
    override fun bind(viewBinding: PictureItemBinding, position: Int) {
        viewBinding.item = item
//        viewBinding.pictureItem.load(Uri.parse(item.picture))
        Glide.with(viewBinding.root.context).load(item.picture).into(viewBinding.pictureItem)
        val broadcast = Intent().apply {
            action = Constants.DELETE_PICTURE
            putExtra(Constants.PICTURE_POSITION, position)
        }
        viewBinding.root.context?.let { ctx ->
            viewBinding.root.setOnLongClickListener {
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> ctx.sendBroadcast(broadcast)
                        DialogInterface.BUTTON_NEGATIVE -> null
                    }
                }
                MaterialAlertDialogBuilder(
                    ctx,
                    R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
                ).apply {
                    setMessage(R.string.delete_picture)
                    setPositiveButton(R.string.yes, dialogClickListener)
                    setNegativeButton(R.string.no, dialogClickListener)
                    create().show()
                }
                it.showContextMenu()
            }
        }
        viewBinding.executePendingBindings()
    }

    override fun initializeViewBinding(view: View): PictureItemBinding {
        return PictureItemBinding.bind(view)
    }
}