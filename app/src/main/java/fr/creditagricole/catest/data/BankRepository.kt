package fr.creditagricole.catest.data

import fr.creditagricole.catest.data.model.Bank

interface BankRepository {

    /**
     * Returns the list of banks
     *
     * @return a [Result] around a [List] of [Bank]
     */
    suspend fun getBanks(): Result<List<Bank>>
}