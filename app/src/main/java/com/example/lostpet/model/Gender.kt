package com.example.lostpet.model

import android.content.Context
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.IgnoredOnParcel
import org.koin.core.KoinComponent
import org.koin.core.inject

data class Gender(@DocumentId val genderId: String,
    val gender: String?
) : KoinComponent {

    constructor():this("","")
    @IgnoredOnParcel
    private val context: Context by inject()
    override fun toString(): String {
        return context.getString(
            context.resources.getIdentifier(
                gender,
                "string",
                context.packageName
            )
        )
    }
}