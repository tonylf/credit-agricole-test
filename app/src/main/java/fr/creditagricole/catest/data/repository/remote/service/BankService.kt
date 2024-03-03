package fr.creditagricole.catest.data.repository.remote.service

import fr.creditagricole.catest.data.repository.remote.response.BankJson
import retrofit2.Response
import retrofit2.http.GET

interface BankService {

    @GET("banks.json")
    suspend fun getBanks(): Response<List<BankJson>>
}