package com.example.minepaper_android.classses

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.wait
import org.json.JSONArray
import java.io.IOException
import java.util.concurrent.Semaphore
import org.json.JSONObject
import java.security.AccessController.getContext

class Utilities {
    companion object {
        fun returnImageListFromServer(): List<String> {

            var output = mutableListOf<String>()
            val semaphore = Semaphore(0)
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .get()
                .url(Constants.IMAGES_LIST_LOCATION)
                .build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    throw e
                }

                override fun onResponse(call: Call, response: Response) {

                    val jsonString = response.body?.string()
                    val json = JSONObject(jsonString)
                    val array = json["files"] as JSONArray

                    for (i in 0 until array.length()) {
                        val file = array.getString(i)
                        output.add(file)
                    }

                    semaphore.release()
                }
            })
            semaphore.acquire()

            return output
        }

        @Composable
        fun isAppInDarkMode(): Boolean {
            val light = MaterialTheme.colorScheme.
        }
    }
}