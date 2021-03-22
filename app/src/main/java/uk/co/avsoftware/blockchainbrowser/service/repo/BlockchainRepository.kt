package uk.co.avsoftware.blockchainbrowser.service.repo

import io.reactivex.rxjava3.core.Single
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.fragvm.blockchain.model.Block
import uk.co.avsoftware.fragvm.blockchain.model.Transaction
import java.math.BigDecimal

interface BlockchainRepository {

    fun currentHashRateGigaHashes(): Single<Long>

    fun getDifficulty(): Single<BigDecimal>

    fun getBlockCount(): Single<Long>

    fun getLatestHash(): Single<String>

    fun getTransactionByHash(tx_hash: String): Single<Transaction>

    fun getLatestBlock(): Single<Block>

    fun getGeneralStats(): Single<Stats>
}