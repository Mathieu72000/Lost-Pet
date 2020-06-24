package com.example.lostpet

sealed class Constants {

    companion object {

        const val RC_SIGN_IN = 1
        const val REQUEST_LOCATION_PERMISSION = 2
        const val TAKE_PICTURE_REQUEST_CODE = 3
        const val PICK_PICTURE_REQUEST_CODE = 4
        const val PERMISSION_REQUEST_CODE = 5
        const val IMAGE_PATH = "image/*"
        const val SHOWCASE_ID = "alreadyUsed"
        const val RESULT_REQUEST_CODE = 6
        const val AUTOCOMPLETE_REQUEST_CODE = 7
        const val ANIMAL_ID = "animalId"
    }
}