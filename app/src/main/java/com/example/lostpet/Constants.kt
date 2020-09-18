package com.example.lostpet

sealed class Constants {

    companion object {

        const val RC_SIGN_IN = 1
        const val REQUEST_LOCATION_PERMISSION = 2
        const val RESULT_REQUEST_CODE = 3
        const val AUTOCOMPLETE_REQUEST_CODE = 4
        const val WRITE_EXTERNAL_STORAGE_REQUEST = 5
        const val CAMERA_PERMISSION_REQUEST = 6
        const val PHONE_CALL_PERMISSION = 7
        const val RECORD_VOICE_PERMISSION = 8
        const val VOICE_SPECIES = 9
        const val VOICE_BREED = 10
        const val VOICE_NAME = 11
        const val VOICE_POSTAL_CODE = 12
        const val VOICE_COLOR = 13
        const val VOICE_IDENTIFICATION_NUMBER = 14
        const val ANIMAL_ID = "animalId"
        const val ANIMAL_COLLECTION = "Animal"
        const val GENDER_COLLECTION = "Gender"
        const val IS_LOST = "lost"
        const val DELETE_PICTURE = "deletePicture"
        const val PICTURE_POSITION = "position"
        const val SHARED_PREFERENCES_NOTIFICATION = "notifications"
        const val SHARED_PREFERENCES_SWITCH = "enable"
        const val SWITCH_STATE = "true"
        const val SEARCH_RESULT = "searchResult"
        const val DELETE_ITEM = "deleteItem"
        const val ITEM_ID = "itemId"
        const val SHOWCASE1 = "material1"
        const val SHOWCASE2 = "material2"
        const val SHOWCASE3 = "material3"
    }
}