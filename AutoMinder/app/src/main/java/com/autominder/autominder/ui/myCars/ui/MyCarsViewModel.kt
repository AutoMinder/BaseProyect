package com.autominder.autominder.ui.myCars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.database.models.CarEntity
import com.autominder.autominder.data.domain.CarModel
import com.autominder.autominder.data.mappers.toCarModel
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCarsViewModel @Inject constructor(
    private val repository: MyCarsRepository
) : ViewModel() {

    /*
    *   Variable declaration section
     */
    var loadedCars = false
    val myCarsList = MutableLiveData<List<CarModel>>()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<OwnCarsUiStatus>(OwnCarsUiStatus.Resume)
    val status: MutableLiveData<OwnCarsUiStatus> = _status

    @OptIn(ExperimentalPagingApi::class)
    val cars = repository.getCarPage(2).map { pagingData ->
        pagingData.map {
            it.toCarModel()
        }
    }.cachedIn(viewModelScope)

    //init is called when the class is instantiated
    init {

    }



    //fetchCarById searches for a car by its id
    /*fun fetchCarById(id: String): CarModel? {

        //return repository.getCarById(id)

        //TODO(): Descomentar cuando se pueda implementar correctamente junto con el CarInfoViewModel:
//        var car: CarModel? = null
//
//        viewModelScope.launch {
//            car = credentialsRepository.getCarById(id)
//        }
//
//        return car
    }*/

    //fetchMyCars fetches the cars owned by the user
//    private fun fetchMyCars() {
//
//            Launching a coroutine
//            viewModelScope.launch {
//
//                //Loading starts
//                setLoading(true)
//
//                //response is the result of the request to the server
//                val response = credentialsRepository.ownCars()
//
//                //postValue is used to update the value of a MutableLiveData object from a background thread
//                _status.postValue(
//                    //Checking the type of response
//                    when (response) {
//                        is ApiResponse.Error ->
//
//                            //If the response is an error, the status is set to Error
//                            OwnCarsUiStatus.Error(response.exception)
//
//                        is ApiResponse.ErrorWithMessage ->
//
//                            //If the response is an error with message, the status is set to ErrorWithMessage
//                            OwnCarsUiStatus.ErrorWithMessage(response.message)
//
//                        is ApiResponse.Success ->
//
//                            //If the response is a success, the status is set to Success
//                            OwnCarsUiStatus.Success(response.data.cars)
//                    }
//                )
//
//                //Loading ends
//                setLoading(false)
//
//                myCarsList.value = repository.getMyCars()
//
//                //TODO(): Descomentar cuando se pueda implementar correctamente junto con el CarInfoViewModel:
//                //myCarsList is set to the list of cars so it can be rendered in the screen
//                //myCarsList.value = (credentialsRepository.ownCars() as ApiResponse.Success<OwnResponse>).data.cars
//            }
//
//    }

    //Sets the loading state
    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }


    //Factory pattern to create the ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {//ViewModelProvider.Factory is an interface that creates ViewModels
            @Suppress("UNCHECKED_CAST")//Suppresses the unchecked cast warning
            override fun <T : ViewModel> create(
                modelClass: Class<T>,//Class of the ViewModel
                extras: CreationExtras//CreationExtras is a class that contains the extras passed to the ViewModelProvider
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                //Returns a MyCarsViewModel
                return MyCarsViewModel(
                    //repository is the repository of the application
                    (application as AutoMinderApplication).myCarsRepository
                ) as T
            }
        }
    }
}