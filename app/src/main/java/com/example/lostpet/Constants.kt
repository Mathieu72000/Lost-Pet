package com.example.lostpet

sealed class Constants {

    companion object {

        const val RC_SIGN_IN = 1
        const val REQUEST_LOCATION_PERMISSION = 2
        const val PERMISSION_REQUEST_CODE = 3
        const val RESULT_REQUEST_CODE = 4
        const val AUTOCOMPLETE_REQUEST_CODE = 5
        const val LOST_RESULT_REQUEST_CODE = 6
        const val SIGN_OUT_REQUEST = 7
        const val SHOWCASE_ID = "alreadyUsed"
        const val ANIMAL_ID = "animalId"
        const val ANIMAL_COLLECTION = "Animal"
        const val GENDER_COLLECTION = "Gender"
        const val PICTURE_COLLECTION = "Pictures"
    }
}