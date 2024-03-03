package fr.creditagricole.catest.domain

import fr.creditagricole.catest.data.BankRepository
import javax.inject.Inject

class GetBanksUseCase @Inject constructor(private val bankRepository: BankRepository) {

    suspend operator fun invoke() = bankRepository.getBanks()
}