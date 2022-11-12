package uk.co.avsoftware.blockchainbrowser.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uk.co.avsoftware.blockchainbrowser.R

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofit(
        gson: Gson,
        @ApplicationContext appContext: Context
    ): Retrofit = Retrofit.Builder()
        .baseUrl(appContext.getString(R.string.api_blockchain_base_url))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

}