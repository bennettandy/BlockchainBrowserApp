package uk.co.avsoftware.blockchainbrowser.service.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.fragvm.blockchain.model.Block
import uk.co.avsoftware.fragvm.blockchain.model.Transaction

interface BlockchainChartingApi {

    @GET("https://api.blockchain.info/stats")
    fun getStats(): Single<Stats>

    @GET("https://blockchain.info/latestblock")
    fun getLatestBlock(): Single<Block>
}