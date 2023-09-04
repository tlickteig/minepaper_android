package com.example.minepaper_android.classses

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Utilities {
    companion object {
        fun returnImageListFromServer(): List<String> {

            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .get()
                .url(Constants.IMAGES_LIST_LOCATION)
                .build()

            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    TODO("Not yet implemented")
                }
            })

            return listOf("String one", "String Two")
        }
    }
}