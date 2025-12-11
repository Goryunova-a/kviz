package com.example.simplequiz

import android.content.Context
import android.content.SharedPreferences

object Utils {
    private const val PREF_NAME = "quiz_prefs"

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getUserName(context: Context): String =
        getPrefs(context).getString("user_name", "Гость") ?: "Гость"

    fun setUserName(context: Context, name: String) {
        getPrefs(context).edit().putString("user_name", name.ifEmpty { "Гость" }).apply()
    }

    fun getLastScore(context: Context): Int =
        getPrefs(context).getInt("last_score", 0)

    fun setLastScore(context: Context, score: Int) {
        getPrefs(context).edit().putInt("last_score", score).apply()
    }
}