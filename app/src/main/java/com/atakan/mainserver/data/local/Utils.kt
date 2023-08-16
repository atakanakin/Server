package com.atakan.mainserver.data.local

import android.os.Environment
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

fun readJson(fileName: String) : String{
    val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val jsonFile = File(downloadsDirectory, fileName)
    val inputStream = FileInputStream(jsonFile)
    val reader = InputStreamReader(inputStream)
    val jsonString = reader.readText()
    return jsonString
}

fun <T> writeJson(fileName: String, written: T){
    val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val jsonFile = File(downloadsDirectory, fileName)
    val inputStream = FileInputStream(jsonFile)
    val json = Gson().toJson(written)
    FileWriter(jsonFile).use { writer ->
        writer.write(json)
    }
}

fun formatDoubleWithCommas(value: Double): String {
    val decimalFormat = DecimalFormat("#,##0.000")
    decimalFormat.maximumFractionDigits = 3
    decimalFormat.minimumFractionDigits = 3
    return decimalFormat.format(value)
}

fun getGreeting(): String {
    val currentTime = Calendar.getInstance()

    return when (currentTime.get(Calendar.HOUR_OF_DAY)) {
        in 0..4 -> "Good night,"
        in 5..11 -> "Good morning,"
        in 12..17 -> "Good afternoon,"
        else -> "Good evening,"
    }
}

fun getCurrentDateFormatted(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return currentDate.format(formatter)
}
