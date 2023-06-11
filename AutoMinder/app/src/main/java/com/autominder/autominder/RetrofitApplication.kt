package com.autominder.autominder

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.autominder.autominder.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.network.retrofit.RetrofitInstance

class RetrofitApplication : Application() {

    //RETROFIT

    private val prefs: SharedPreferences by lazy{
        getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
    }

    private fun getAPIService() = with(RetrofitInstance){
        setToken(getToken())
        getLoginService()
    }

    private fun getToken() = prefs.getString(USER_TOKEN, "")!!

    val credentialsRepository: CredentialsRepository by lazy {
        CredentialsRepository(getAPIService())
    }

    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    companion object{
        const val USER_TOKEN = "user_token"
    }






}