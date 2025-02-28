package com.medicare.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferences {

    private const val SHARED_PREF_NAME="TempData"
    private const val IS_USER_DOCTOR="isUserDoctor"

    fun isUserDoctor(context:Context):Boolean{
        val sp = context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE)
        return sp.getBoolean(IS_USER_DOCTOR,false)
    }

    fun setUserDoctor(context:Context,value:Boolean){
        val sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putBoolean(IS_USER_DOCTOR,value)
        editor.apply()
    }

}