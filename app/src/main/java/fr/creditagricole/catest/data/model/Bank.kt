package fr.creditagricole.catest.data.model

data class Bank(
    val name: String,
    val isCreditAgricole: Boolean,
    val accounts: List<Account>
)