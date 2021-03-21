package uk.co.avsoftware.fragvm.blockchain.model

data class Transaction(
    val hash: String,
    val ver: Int,
    val vin_sz: Int,
    val vout_sz: Int,
    val lock_time: String,
    val size: Int,
    val relayed_by: String,
    val block_height: Int,
    val tx_index: String,
    val inputs: List<TransactionInput>,
    val out: List<TransactionOutput>
)

data class TransactionInput(
    val prev_out: PreviousOutput,
    val script: String
)

data class PreviousOutput(
    val hash: String,
    val value: String,
    val tx_index: String,
    val n: String
)

data class TransactionOutput(
    val value: String,
    val hash: String,
    val script: String
)