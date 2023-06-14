package com.autominder.autominder.ui.myCars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.database.models.UserWithCars
import com.autominder.autominder.data.database.repository.CarRepository
import com.autominder.autominder.data.models_dummy.CarModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MyCarsViewModel(
    private val repository: CarRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val myCarsList = MutableLiveData<List<CarModel>>()
    val userWithCars = MutableLiveData<List<UserWithCars>>()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    val userId = savedStateHandle.get<Long>("userId") ?: 0L
    val Cars = MutableStateFlow<com.autominder.autominder.data.database.models.CarModel>(
        com.autominder.autominder.data.database.models.CarModel(
            0,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            true,
            213,
        )
    )


    init {
        fetchMyCars()
    }

    suspend fun fetchCarById(id: String): com.autominder.autominder.data.database.models.CarModel {
        return repository.getCarById(id)
    }

    private fun fetchMyCars() {

        viewModelScope.launch {
            try {
                setLoading(true)
                delay(100)
                userWithCars.value = repository.getMyCars(userId)
                Cars.value = repository.getCarById("1")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }

    suspend fun getAllCars() = repository.getMyCars(userId)

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                MyCarsViewModel(
                    app.carRepository,
                    createSavedStateHandle()
                )
            }
        }
    }
}