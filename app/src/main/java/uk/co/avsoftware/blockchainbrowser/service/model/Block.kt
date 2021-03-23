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