package uk.co.avsoftware.blockchainbrowser.service.repo

import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal

interface BlockchainRepository {
    fun currentHashRateGigaHashes(): Single<Long>

    fun getDifficulty(): Single<BigDecimal>

    fun getBlockCount(): Single<Long>

    fun getLatestHash(): Single<String>
}