package com.example.attractions.data.db

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.attractions.data.model.Photo
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


class Repository @Inject constructor(private val photoDao: PhotoDao) {

    fun getAllData(): Flow<List<Photo>> {
        return photoDao.getAll()
    }


//    @SuppressLint("WeekBasedYear")
//    private val dataPhoto =
//        SimpleDateFormat(DATA_PHOTO_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())

    suspend fun insertData(context: Context) {
        photoDao.insert(Photo(getUriLastPhoto(context)))
    }

    @SuppressLint("Recycle")
    private fun getUriLastPhoto(context: Context): String {
        var returnedUri = ""
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor = context.contentResolver.query(
            uri, projection, null,
            null, null
        )
        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursor.moveToNext()) {
            if (cursor.isLast) {
                returnedUri = cursor.getString(columnIndexData)
            }
        }
        return returnedUri
    }

//    companion object {
//        private const val DATA_PHOTO_FORMAT = "dd.MM.YYYY HH:mm:ss"
//    }

}