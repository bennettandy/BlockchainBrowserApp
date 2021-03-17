package uk.co.avsoftware.blockchainbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uk.co.avsoftware.blockchainbrowser.service.repo.api.BlockchainApi

@Module
@InstallIn(SingletonComponent::class)
object BlockchainModule {

    @Provides
    fun provideBlockchainApi( retrofit: Retrofit): BlockchainApi = retrofit.create(BlockchainApi::class.java)
}