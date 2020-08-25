package com.example.lostpet.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.lostpet.itemAdapter.PostsAnimalItem
import com.example.lostpet.repository.AnimalRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {

    private val repository = AnimalRepository()

    @ExperimentalCoroutinesApi
    val userAnimalList = liveData(Dispatchers.IO) {
        repository.getUserCollection(getCurrentUser()?.uid ?: "").collect {
            emit(it)
        }
    }

    val itemList = Transformations.map(userAnimalList) { animal ->
        animal.map {
            PostsAnimalItem(AnimalItemViewModel(it))
        }
    }


    fun deleteItem(documentId: String) {
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            repository.deleteItem(documentId)
        }
    }

    private fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}