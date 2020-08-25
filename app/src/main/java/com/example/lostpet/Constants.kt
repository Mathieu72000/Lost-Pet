package com.example.lostpet

sealed class Constants {

    companion object {

        const val RC_SIGN_IN = 1
        const val REQUEST_LOCATION_PERMISSION = 2
        const val RESULT_REQUEST_CODE = 3
        const val AUTOCOMPLETE_REQUEST_CODE = 4
        const val WRITE_EXTERNAL_STORAGE_REQUEST = 5
        const val CAMERA_PERMISSION_REQUEST = 6
        const val SIGN_OUT_REQUEST = 7
        const val PHONE_CALL_PERMISSION = 8
        const val SHOWCASE_ID = "alreadyUsed"
        const val ANIMAL_ID = "animalId"
        const val ANIMAL_COLLECTION = "Animal"
        const val GENDER_COLLECTION = "Gender"
        const val USER_COLLECTION = "User"
        const val IS_LOST = "lost"
        const val DELETE_PICTURE = "deletePicture"
        const val PICTURE_POSITION = "position"
        const val SHARED_PREFERENCES_NOTIFICATION = "notifications"
        const val SHARED_PREFERENCES_SWITCH = "enable"
        const val SWITCH_STATE = "true"
        const val SEARCH_RESULT = "searchResult"
        const val IS_SEARCH_CONTEXT = "search"
        const val DELETE_ITEM = "deleteItem"
        const val ITEM_ID = "itemId"
    }
}