package fr.creditagricole.catest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.creditagricole.catest.data.BankRepository
import fr.creditagricole.catest.data.repository.BankDataRepository
import fr.creditagricole.catest.data.repository.BankDataSource
import javax.inject.Singleton

/**
 * Hilt module (Dependency Injection) for Repositories
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBankRepository(bankRemoteDataSource: BankDataSource): BankRepository =
        BankDataRepository(bankRemoteDataSource)
}