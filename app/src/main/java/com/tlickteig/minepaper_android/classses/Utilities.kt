package com.tlickteig.minepaper_android.classses

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.util.concurrent.Semaphore
import org.json.JSONObject
import com.tlickteig.minepaper_android.BuildConfig
import java.net.URL

class Utilities {
    companion object {
        enum class WallpaperFlags {
            LOCK, HOME, BOTH
        }

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

        fun getVersionString(): String {
            return BuildConfig.VERSION_NAME;
        }

        fun setWallpaper(imageName: String, context: Context, flags: WallpaperFlags = WallpaperFlags.BOTH) {
            try {
                var thread = Thread {
                    val url = URL("${Constants.CDN_URL}/${imageName}")
                    val image = BitmapFactory.decodeStream(
                        url.openConnection().getInputStream()
                    )
                    val wallpaperManager =
                        WallpaperManager.getInstance(context)

                    when (flags) {
                        WallpaperFlags.LOCK -> {
                            wallpaperManager.setBitmap(image, null, false, WallpaperManager.FLAG_LOCK)
                        }
                        WallpaperFlags.HOME -> {
                            wallpaperManager.setBitmap(image, null, false, WallpaperManager.FLAG_SYSTEM)
                        }
                        else -> {
                            wallpaperManager.setBitmap(image)
                        }
                    }
                }
                thread.start()
            }
            catch (e: Exception) {
                println(e)
            }
        }
    }
}