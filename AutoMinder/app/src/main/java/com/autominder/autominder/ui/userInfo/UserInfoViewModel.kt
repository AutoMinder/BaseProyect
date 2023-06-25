package com.autominder.autominder.ui.userInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.data.network.dto.whoami.WhoamiResponse
import com.autominder.autominder.ui.myCars.ui.OwnCarsUiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class UserInfoViewModel(
    private val repository: CredentialsRepository)
    : ViewModel()
{
    init{
        fetchUserName()
    }

    private val _apiData = MutableLiveData<String>()
    val apiData: LiveData<String> get() = _apiData

    private val _status = MutableLiveData<UserInfoUiStatus>(UserInfoUiStatus.Resume)
    val status: MutableLiveData<UserInfoUiStatus> get() = _status

    fun onBuyLinkClicked(context: Context) {
        val url =
            "https://www.amazon.com/?&tag=googleglobalp-20&ref=pd_sl_7nnedyywlk_e&adgrpid=82342659060&hvpone=&hvptwo=&hvadid=585475370855&hvpos=&hvnetw=g&hvrand=8109794863544036515&hvqmt=e&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9069896&hvtargid=kwd-10573980&hydadcr=2246_13468515"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun onContactDevelopersClicked(context: Context) {
        val address = "mailto:00077321@uca.edu.sv" //TODO: Cambiar
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(address)
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
        }
        context.startActivity(intent)
    }

    fun fetchUserName() {
        viewModelScope.launch {
            try {
                val response = repository.myInfo() // Replace with your API call
                _apiData.value = response.username

//                _status.postValue(
//                    when (response) {
//                        is ApiResponse.Error -> UserInfoUiStatus.Error(response.exception)
//                        is ApiResponse.ErrorWithMessage -> UserInfoUiStatus.ErrorWithMessage(
//                            response.message
//                        )
//
//                        is ApiResponse.Success -> UserInfoUiStatus.Success(response.data.username)
//                    }
//                )

//                _status.value = response.data.username?.let { UserInfoUiStatus.Success(it) }
//
//                Log.d("VM", "Name: $name")
            } catch (e: Exception) {
                _apiData.value = "Usuario"
            }
        }
    }

    suspend fun onLogoutClicked() {
        _apiData.value = ""
        repository.logout()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AutoMinderApplication
                UserInfoViewModel(app.credentialsRepository)
            }
        }

    }
}