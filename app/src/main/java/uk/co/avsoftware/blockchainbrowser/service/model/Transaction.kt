package uk.co.avsoftware.blockchainbrowser.service.model

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
    val sequence: Long,
    val witness: String,
    val index: Int,
    val prev_out: PreviousOutput,
    val script: String
)

data class PreviousOutput(
    val hash: String,
    val value: String,
    val spending_outpoints: List<Outpoint>,
    val tx_index: String,
    val n: String
)

data class TransactionOutput(
    val value: Long,
    val hash: String,
    val script: String,
    val spent: Boolean,
    val spending_outpoints: List<Outpoint>,
    val addr: String,
    val n: Int,
)


data class Outpoint(
    val tx_index: Long,
    val n: Int
)
