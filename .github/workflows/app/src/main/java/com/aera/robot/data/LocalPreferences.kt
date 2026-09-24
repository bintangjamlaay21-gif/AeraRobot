package com.aera.robot.data

import android.content.Context
import android.content.SharedPreferences

class LocalPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("aera_prefs", Context.MODE_PRIVATE)

    var robotName: String
        get() = prefs.getString("robot_name", "Aera") ?: "Aera"
        set(value) = prefs.edit().putString("robot_name", value).apply()

    var voiceSpeed: Float
        get() = prefs.getFloat("voice_speed", 1.0f)
        set(value) = prefs.edit().putFloat("voice_speed", value).apply()
}
