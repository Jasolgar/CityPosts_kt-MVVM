package es.jasolgar.cityposts_kt.utils

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*


class CommonUtils {

    companion object {
        fun generateRandomColorHex(): String {
            val zeros = "000000"
            val rnd = Random()
            var s = rnd.nextInt(0X1000000).toString(16)
            s = zeros.substring(s.length) + s
            return s
        }

        @Throws(IOException::class)
        fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
            val manager: AssetManager = context.assets
            val inputStream: InputStream = manager.open(jsonFileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, StandardCharsets.UTF_8)
        }

        @Throws(IOException::class)
        fun loadFileFromAsset(context: Context, fileName: String): Bitmap {
            val manager: AssetManager = context.assets
            manager.open(fileName)
            return BitmapFactory.decodeStream(manager.open(fileName))
        }
    }

}