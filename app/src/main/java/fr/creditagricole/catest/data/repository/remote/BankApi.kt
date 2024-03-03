package fr.creditagricole.catest.data.repository.remote

import fr.creditagricole.catest.data.model.Bank

interface BankApi {

    /**
     * Returns the list of Banks with their accounts and operations
     *
     * @return a [List] of [Bank]
     */
    suspend fun getBanks(): List<Bank>
}