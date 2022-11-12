@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection"
)

package uk.co.avsoftware.blockchainbrowser.service.api

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import java.math.BigDecimal

interface BlockchainSimpleQueryApi {

    /**
     * Estimated network hash rate in gigahash
     */
    @GET("/q/hashrate")
    fun currentHashRateGigaHashes(): Flow<Long>

    /**
     * Current difficulty target as a decimal number
     */
    @GET("/q/getdifficulty")
    fun getDifficulty(): Flow<BigDecimal>

    /**
     * Current block height in the longest chain
     */
    @GET("/q/getblockcount")
    fun getBlockCount(): Flow<Long>

    /**
     * Hash of the latest block
     */
    @GET("/q/latesthash")
    fun getLatestHash(): Flow<String>
}