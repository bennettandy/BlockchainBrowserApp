package uk.co.avsoftware.blockchainbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainRestApi
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainSimpleQueryApi

@Module
@InstallIn(SingletonComponent::class)
object BlockchainModule {

    @Provides
    fun provideSimpleQueryApi( retrofit: Retrofit): BlockchainSimpleQueryApi = retrofit.create(BlockchainSimpleQueryApi::class.java)

    @Provides
    fun providesRestApi( retrofit: Retrofit): BlockchainRestApi = retrofit.create(BlockchainRestApi::class.java)
}