package fr.creditagricole.catest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.creditagricole.catest.data.repository.BankDataSource
import fr.creditagricole.catest.data.repository.remote.BankApi
import fr.creditagricole.catest.data.repository.remote.BankRemoteDataSource

/**
 * Hilt module (Dependency Injection) for Data Sources
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideBankRemoteDataSource(bankApi: BankApi): BankDataSource =
        BankRemoteDataSource(bankApi)
}