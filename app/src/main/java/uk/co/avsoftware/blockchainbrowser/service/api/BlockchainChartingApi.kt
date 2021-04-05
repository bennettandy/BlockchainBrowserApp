package uk.co.avsoftware.blockchainbrowser.service.api

import retrofit2.http.GET
import uk.co.avsoftware.blockchainbrowser.service.model.Stats

interface BlockchainChartingApi {

    @GET("https://api.blockchain.info/stats")
    suspend fun  getStats(): Stats

}