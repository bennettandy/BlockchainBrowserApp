package uk.co.avsoftware.blockchainbrowser.service.repo

import io.reactivex.rxjava3.core.Single

interface BlockchainRepository {
    fun currentHashRateGigaHashes(): Single<Long>
}