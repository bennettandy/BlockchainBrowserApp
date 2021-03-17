package uk.co.avsoftware.blockchainbrowser.service.repo.api

import io.reactivex.rxjava3.core.Single

interface BlockchainApi {
    fun currentHashRateGigaHashes(): Single<Long>
}