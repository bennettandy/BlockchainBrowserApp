package uk.co.avsoftware.blockchainbrowser.service.repo.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BlockchainApi {

    @GET("/q/hashrate")
    fun currentHashRateGigaHashes(): Single<Long>
}