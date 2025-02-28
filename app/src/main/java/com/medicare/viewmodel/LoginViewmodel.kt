package com.medicare.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicare.data.FirebaseInstance
import com.medicare.data.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewmodel: ViewModel() {

    val isUserDoctor = MutableLiveData<Boolean>(true)

    fun getSignInIntent(): Intent {
        return FirebaseInstance.getSignInIntent()
    }

    fun setHomePage(context: Context){
        viewModelScope.launch(Dispatchers.IO){
           SharedPreferences.setUserDoctor(context,isUserDoctor.value!!)
        }
    }
}