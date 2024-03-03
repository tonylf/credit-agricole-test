package fr.creditagricole.catest.data.repository.remote.service

import fr.creditagricole.catest.data.model.Bank
import fr.creditagricole.catest.data.repository.remote.BankApi
import fr.creditagricole.catest.data.repository.remote.mapper.BankMapper
import retrofit2.HttpException
import javax.inject.Inject

class BankApiImpl @Inject constructor(private val bankService: BankService) : BankApi {

    override suspend fun getBanks(): List<Bank> {
        val response = bankService.getBanks()

        if (response.isSuccessful) {
            return BankMapper.transform(response.body())
        } else {
            throw HttpException(response)
        }
    }
}