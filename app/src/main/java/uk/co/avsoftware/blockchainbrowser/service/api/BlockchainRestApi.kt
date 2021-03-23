package uk.co.avsoftware.blockchainbrowser.service.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.avsoftware.blockchainbrowser.service.model.Block
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction

interface BlockchainRestApi {

    @GET("https://blockchain.info/rawtx/{txHash}")
    fun getTransactionByHash(@Path("txHash") txHash: String): Single<Transaction>

    @GET("https://blockchain.info/latestblock")
    fun getLatestBlock(): Single<Block>

    @GET("https://blockchain.info/rawblock/{blockHash}")
    fun getRawBlockByHash(@Path("blockHash") blockHash: String): Single<Block>
}