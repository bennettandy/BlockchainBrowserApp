package uk.co.avsoftware.blockchainbrowser.service.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction
import uk.co.avsoftware.fragvm.blockchain.model.Block

interface BlockchainRestApi {

    @GET("https://blockchain.info/rawtx/{tx_hash}")
    fun getTransactionByHash(@Path("tx_hash") tx_hash: String): Single<Transaction>

    @GET("https://blockchain.info/latestblock")
    fun getLatestBlock(): Single<Block>

    @GET("https://blockchain.info/rawblock/{block_hash}")
    fun getRawBlockByHash(@Path("block_hash") block_hash: String): Single<Block>
}