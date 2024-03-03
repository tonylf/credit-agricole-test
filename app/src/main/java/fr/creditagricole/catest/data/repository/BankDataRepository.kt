package fr.creditagricole.catest.data.repository

import fr.creditagricole.catest.data.BankRepository
import fr.creditagricole.catest.data.model.Bank
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BankDataRepository @Inject constructor(
    private val bankRemoteDataSource: BankDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BankRepository {

    override suspend fun getBanks(): Result<List<Bank>> =
        withContext(ioDispatcher) {
            bankRemoteDataSource.getBanks()
        }
}