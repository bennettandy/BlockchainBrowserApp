package uk.co.avsoftware.blockchainbrowser.service.model

import com.google.gson.annotations.SerializedName

//@Entity(tableName = "blocks")
data class Block(
    val hash: String,
    val time: Long,
    @SerializedName("block_index")
    val blockIndex: Int,
    val height: Int,

    @SerializedName("prev_block")
    val previousBlock: String,
    @SerializedName("mrkl_root")
    val merkleRoot: String,

    val tx: List<Transaction>
) {
    //@PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        // todo: remove this
        fun tempTest(): Block = Block("hash", 0,0,0,"0000123","bfdbsfa",
            listOf(Transaction("dfsdfasdfdfdfad", 0, 0, 0, "",0, "", 0, "", emptyList(), emptyList())))
    }
}

data class Transaction(
    val hash: String,
    val ver: Int,
    @SerializedName("vin_sz")
    val vinSz: Int,
    @SerializedName("vout_sz")
    val voutSz: Int,
    @SerializedName("lock_time")
    val lockTime: String,
    val size: Int,
    @SerializedName("relayed_by")
    val relayedBy: String,
    @SerializedName("block_height")
    val blockHeight: Int,
    @SerializedName("tx_index")
    val txIndex: String,
    val inputs: List<TransactionInput>,
    val out: List<TransactionOutput>
)

data class TransactionInput(
    val sequence: Long,
    val witness: String,
    val index: Int,
    @SerializedName("prev_out")
    val prevOut: PreviousOutput,
    val script: String
)

data class PreviousOutput(
    val hash: String,
    val value: String,
    @SerializedName("spending_outpoints")
    val spendingOutpoints: List<Outpoint>,
    @SerializedName("tx_index")
    val txIndex: String,
    val n: String
)

data class TransactionOutput(
    val value: Long,
    val hash: String,
    val script: String,
    val spent: Boolean,
    @SerializedName("spending_outpoints")
    val spendingOutpoints: List<Outpoint>,
    val addr: String,
    val n: Int,
)

data class Outpoint(
    @SerializedName("tx_index")
    val txIndex: Long,
    val n: Int
)