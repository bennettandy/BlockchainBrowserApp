package uk.co.avsoftware.blockchainbrowser.service.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Stats(
    @SerializedName("timestamp")
    val timestamp: BigDecimal  = BigDecimal.ZERO,
    @SerializedName("market_price_usd")
    val marketPriceUsd: String = "0.00",
    @SerializedName("hash_rate")
    val hashRate: BigDecimal = BigDecimal.ZERO,
    @SerializedName("total_fees_btc")
    val totalFeesBtc: Long = 0L,
    @SerializedName("n_btc_mined")
    val nBtcMined: Long = 0L,
    @SerializedName("n_tx")
    val nTx: Long = 0L,
    @SerializedName("n_blocks_mined")
    val nBlocksMined: Long = 0L,
    @SerializedName("minutes_between_blocks")
    val minutesBetweenBlocks: String = "0",
    @SerializedName("totalbtc")
    val totalBtc: Long = 0L,
    @SerializedName("n_blocks_total")
    val nBlocksTotal: Long = 0L,
    @SerializedName("estimated_transaction_volume_usd")
    val estimatedTransactionVolumeUsd: BigDecimal = BigDecimal.ZERO,
    @SerializedName("blocks_size")
    val blocksSize: Long = 0L,
    @SerializedName("miners_revenue_usd")
    val minersRevenueUsd: BigDecimal = BigDecimal.ZERO,
    @SerializedName("nextretarget")
    val nextretarget: Long = 0L,
    @SerializedName("difficulty")
    val difficulty: Long = 0L,
    @SerializedName("estimated_btc_sent")
    val estimatedBtcSent: Long = 0L,
    @SerializedName("miners_revenue_btc")
    val minersRevenueBtc: Long = 0L,
    @SerializedName("total_btc_sent")
    val totalBtcSent: Long = 0L,
    @SerializedName("trade_volume_btc")
    val tradeVolumeBtc: BigDecimal = BigDecimal.ZERO,
    @SerializedName("trade_volume_usd")
    val tradeVolumeUsd: BigDecimal = BigDecimal.ZERO

)
