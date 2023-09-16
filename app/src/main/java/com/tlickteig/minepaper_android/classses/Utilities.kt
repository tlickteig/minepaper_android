package com.tlickteig.minepaper_android.classses

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
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
            var hasNetworkErrorOccurred = false

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    hasNetworkErrorOccurred = true
                    semaphore.release()
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
            if (hasNetworkErrorOccurred) {
                throw IOException("A network error has occurred. Please try again later")
            }

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

        fun saveImageToGallery(imageName: String, context: Context) {
            try {
                if (isExternalStorageWritable()) {
                    var thread = Thread {
                        val url = URL("${Constants.CDN_URL}/${imageName}")
                        val image = BitmapFactory.decodeStream(
                            url.openConnection().getInputStream()
                        )

                        val values = ContentValues().apply {
                            put(MediaStore.Images.Media.TITLE, imageName)
                            put(
                                MediaStore.Images.Media.DESCRIPTION,
                                "Wallpaper downloaded from MinePaper"
                            )
                            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        }

                        val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        val resolver = context.contentResolver
                        val uri = resolver.insert(imageCollection, values)

                        val outputStream: OutputStream? = uri?.let { resolver.openOutputStream(it) }
                        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream?.close()
                    }
                    thread.start()
                }
            }
            catch (e: Exception) {
                println(e)
            }
        }

        fun shareImage(imageName: String, context: Context) {
            try {
                var thread = Thread {
                    try {
                        val url = URL("${Constants.CDN_URL}/${imageName}")
                        val image = BitmapFactory.decodeStream(
                            url.openConnection().getInputStream()
                        )

                        val cachePath = File(context.cacheDir, "images")
                        cachePath.mkdirs()

                        val file = File(cachePath, imageName)
                        val fos = FileOutputStream(file)

                        image.compress(Bitmap.CompressFormat.PNG, 90, fos)
                        fos.flush()
                        fos.close()
                        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)

                        val intent = Intent(Intent.ACTION_SEND)
                        intent.putExtra(Intent.EXTRA_STREAM, uri)
                        intent.setType("image/png");
                        context.startActivity(Intent.createChooser(intent, "Share Via"));
                    }
                    catch (e: Exception) {
                        println(e)
                    }
                }
                thread.start()
            }
            catch (e: Exception) {
                println(e)
            }
        }

        fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

        fun openImageInBrowser(imageName: String, context: Context) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("${Constants.CDN_URL}/${imageName}"))
            context.startActivity(browserIntent)
        }
    }
}