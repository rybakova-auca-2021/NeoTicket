package com.example.neoticket.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

object QRCodeUtils {
    private const val IMAGE_DIRECTORY = "/QRCodeImages"

    @Throws(WriterException::class)
    fun textToImageEncode(value: String, qrCodeWidth: Int): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                value,
                BarcodeFormat.QR_CODE, // Corrected import statement
                qrCodeWidth,
                qrCodeWidth,
                null
            )
        } catch (e: IllegalArgumentException) {
            return null
        }

        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth
            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] = if (bitMatrix[x, y]) {
                    Color.BLACK
                } else {
                    Color.WHITE
                }
            }
        }

        return Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888).apply {
            setPixels(pixels, 0, qrCodeWidth, 0, 0, bitMatrixWidth, bitMatrixHeight)
        }
    }

    fun saveImage(context: Context, myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

        val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val wallpaperDirectory = File(externalFilesDir, IMAGE_DIRECTORY)

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(
                wallpaperDirectory,
                "${Calendar.getInstance().timeInMillis}.jpg"
            )
            f.createNewFile()

            FileOutputStream(f).use { fo ->
                fo.write(bytes.toByteArray())
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(f.path),
                    arrayOf("image/jpeg"),
                    null
                )
                Log.d("TAG", "File Saved::--->${f.absolutePath}")
                return f.absolutePath
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("TAG", "Error saving image: ${e.message}")
        }
        return ""
    }

    object BarcodeUtils {
        fun generateBarcode(value: String, barcodeWidth: Int, barcodeHeight: Int): Bitmap? {
            val multiFormatWriter = MultiFormatWriter()
            try {
                val bitMatrix = multiFormatWriter.encode(value, BarcodeFormat.CODE_128, barcodeWidth, barcodeHeight)
                val barcodeEncoder = BarcodeEncoder()
                return barcodeEncoder.createBitmap(bitMatrix)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
            return null
        }
    }

    class BarcodeEncoder {
        fun createBitmap(matrix: BitMatrix): Bitmap {
            val width = matrix.width
            val height = matrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            return bmp
        }
    }

}

