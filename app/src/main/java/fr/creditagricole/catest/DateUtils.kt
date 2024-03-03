package fr.creditagricole.catest

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

    fun formatDate(date: Date): String = dateFormat.format(date)
}