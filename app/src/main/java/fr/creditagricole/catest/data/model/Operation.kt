package fr.creditagricole.catest.data.model

import java.util.Date

data class Operation(
    val id: String,
    val title: String,
    val amount: String,
    val category: String,
    val date: Date
)
