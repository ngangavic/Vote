package com.ngangavictor.vote

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("VoteApp", Context.MODE_PRIVATE)

    fun savePref(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun readPref(key: String): String {
        return sharedPref.getString(key, null).toString()
    }

    fun clearPref() {
        sharedPref.edit().clear().apply()
    }

    fun checkExist(key: String): Boolean {
        return sharedPref.contains(key)
    }
}