package com.aera.robot.data

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiRepository {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "MASUKKAN_API_KEY_ANDA_DISINI"
    )

    suspend fun getResponse(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(prompt)
                response.text ?: "Maaf, tidak ada respons dari Gemini."
            } catch (e: Exception) {
                "Terjadi kesalahan: ${e.localizedMessage}"
            }
        }
    }
}
