package uk.co.avsoftware.fragvm.blockchain.model

import com.google.gson.annotations.SerializedName
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction

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
}