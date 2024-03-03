package fr.creditagricole.catest.data.repository.remote

import fr.creditagricole.catest.data.model.Bank
import fr.creditagricole.catest.data.repository.BankDataSource
import javax.inject.Inject

class BankRemoteDataSource @Inject constructor(private val bankApi: BankApi) : BankDataSource {

    override suspend fun getBanks(): Result<List<Bank>> =
        try {
            Result.success(bankApi.getBanks())
        } catch (e: Exception) {
            Result.failure(e)
        }
}