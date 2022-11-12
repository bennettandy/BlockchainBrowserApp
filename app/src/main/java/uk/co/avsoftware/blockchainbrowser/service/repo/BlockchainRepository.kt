package uk.co.avsoftware.blockchainbrowser.service.repo

import uk.co.avsoftware.blockchainbrowser.service.model.Block
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction
import java.math.BigDecimal

interface BlockchainRepository {

    suspend fun currentHashRateGigaHashes(): Long

    suspend fun getDifficulty(): BigDecimal

    suspend fun getBlockCount(): Long

    suspend fun getLatestHash(): String

    suspend fun getTransactionByHash(txHash: String): Transaction?

    suspend fun getLatestBlock(): Block?

    suspend fun getGeneralStats(): Stats?
}