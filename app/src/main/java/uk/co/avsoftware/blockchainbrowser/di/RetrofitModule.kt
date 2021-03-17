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
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.avsoftware.blockchainbrowser.R

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        @ApplicationContext appContext: Context
    ): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(converterFactory)
        .baseUrl(appContext.getString(R.string.api_blockchain_base_url))
        .build();

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideConverterFactory(gson: Gson) = GsonConverterFactory
        .create(gson)

}