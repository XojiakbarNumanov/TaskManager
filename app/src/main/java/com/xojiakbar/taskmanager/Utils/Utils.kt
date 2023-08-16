package com.xojiakbar.taskmanager.Utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import com.xojiakbar.taskmanager.api.result.ErrorResult
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale

object Utils {
    fun getLocalStringByResId(context: Context, resId: Int): String? {
        val appLang = Preferences.getAppLanguage()
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(appLang))
        return context.createConfigurationContext(configuration).resources.getString(resId)
    }
    fun errorParser(responseBody: ResponseBody): ErrorResult? {
        var errorResult = ErrorResult(-1, -9999, "", "")
        try {
            val jObjError = JSONObject(responseBody.string())
            errorResult.code = (jObjError.getJSONObject("ERROR").getInt("code"))
            errorResult.status = (jObjError.getJSONObject("ERROR").getInt("status"))
            if (jObjError.getJSONObject("ERROR")
                    .getString("message") != "null"
            ) errorResult.message = (jObjError.getJSONObject("ERROR").getString("message"))
            if (jObjError.getJSONObject("ERROR")
                    .getString("description") != "null"
            ) errorResult.description = (jObjError.getJSONObject("ERROR").getString("description"))
            if (jObjError.getJSONObject("ERROR").has("download_url")) {
                errorResult.download_url = (
                    jObjError.getJSONObject("ERROR").getString("download_url")
                )
            }
            if (jObjError.getJSONObject("ERROR").has("has_new_version")) {
                errorResult.isHas_new_version = (
                    jObjError.getJSONObject("ERROR").getBoolean("has_new_version")
                )
            }
            if (jObjError.getJSONObject("ERROR").has("version_name")) {
                errorResult.version_name = (
                    jObjError.getJSONObject("ERROR").getString("version_name")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (errorResult.message == null) errorResult = ErrorResult(-1, -1, "", "")
        return errorResult
    }
    fun getStringDigest(password: String): String? {
        var s: String? = null
        try {
            val digest = MessageDigest.getInstance("SHA1")
            digest.update(password.toByteArray())
            s = toHexString(digest.digest())
                ?.lowercase(Locale.getDefault())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return s
    }
open fun toHexString(block: ByteArray): String? {
    val buf = StringBuffer()
    for (b in block) {
        byte2hex(b, buf)
    }
    return buf.toString()
}
    private fun byte2hex(b: Byte, buf: StringBuffer) {
        val hexChars = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
        )
        val high = b.toInt() and 0xf0 shr 4
        val low = b.toInt() and 0xf
        buf.append(hexChars[high])
        buf.append(hexChars[low])
    }
    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            var selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()
            selectedBitmap = checkRotation(
                selectedBitmap!!,
                file
            )
            // here i override the original image file
            if (file.exists() || file.createNewFile()) {
                val outputStream = FileOutputStream(file)
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    fun checkRotation(bitmap: Bitmap, file: File): Bitmap {
        val ei = ExifInterface(file.absolutePath)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        val rotatedBitmap: Bitmap
        rotatedBitmap =
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(
                    bitmap,
                    90f
                )

                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(
                    bitmap,
                    180f
                )

                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(
                    bitmap,
                    270f
                )

                ExifInterface.ORIENTATION_NORMAL -> bitmap
                else -> bitmap
            }
        return rotatedBitmap
    }
    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
    fun getMonths(): ArrayList<String>? {
        val app_language = Preferences.getAppLanguage()
        val months = ArrayList<String>()
        if (app_language == "ru") {
            months.add("Январь")
            months.add("Февраль")
            months.add("Март")
            months.add("Апрель")
            months.add("Май")
            months.add("Июнь")
            months.add("Июль")
            months.add("Август")
            months.add("Сентябрь")
            months.add("Октябрь")
            months.add("Ноябрь")
            months.add("Декабрь")
        } else if (app_language == "uz") {
            months.add("Yanvar")
            months.add("Fevral")
            months.add("Mart")
            months.add("Aprel")
            months.add("May")
            months.add("Iyun")
            months.add("Iyul")
            months.add("Avgust")
            months.add("Sentyabr")
            months.add("Oktyabr")
            months.add("Noyabr")
            months.add("Dekabr")
        } else {
            months.add("Январ")
            months.add("Феврал")
            months.add("Март")
            months.add("Апрел")
            months.add("Май")
            months.add("Июн")
            months.add("Июл")
            months.add("Август")
            months.add("Сентябр")
            months.add("Октябр")
            months.add("Ноябрь")
            months.add("Декабр")
        }
        return months
    }


}