package com.pt.dog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pt.dog.model.Breeds
import com.pt.dog.usecase.BreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val dogUseCase: BreedsUseCase) : ViewModel() {

    private val _breedsLiveData: MutableLiveData<List<Breeds>> = MutableLiveData()
    val breedsLiveData: LiveData<List<Breeds>> get() = _breedsLiveData

    private val _breedsErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val breedsErrorLiveData: LiveData<String> get() = _breedsErrorLiveData

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val dogs = dogUseCase.getDogs()
                _breedsLiveData.value = dogs
            } catch (e: Exception) {
                _breedsErrorLiveData.value = e.message
            }
        }
    }
}