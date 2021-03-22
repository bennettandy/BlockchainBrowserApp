package uk.co.avsoftware.blockchainbrowser.service.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import uk.co.avsoftware.blockchainbrowser.service.model.Stats

interface BlockchainChartingApi {

    @GET("https://api.blockchain.info/stats")
    fun getStats(): Single<Stats>

}