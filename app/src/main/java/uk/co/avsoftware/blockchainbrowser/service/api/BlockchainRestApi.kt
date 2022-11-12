package uk.co.avsoftware.blockchainbrowser.service.api

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.avsoftware.blockchainbrowser.service.model.Block
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction

interface BlockchainRestApi {

    @GET("https://blockchain.info/rawtx/{txHash}")
    fun getTransactionByHash(@Path("txHash") txHash: String): Flow<Transaction>

    @GET("https://blockchain.info/latestblock")
    fun getLatestBlock(): Flow<Block>

    @GET("https://blockchain.info/rawblock/{blockHash}")
    fun getRawBlockByHash(@Path("blockHash") blockHash: String): Flow<Block>
}