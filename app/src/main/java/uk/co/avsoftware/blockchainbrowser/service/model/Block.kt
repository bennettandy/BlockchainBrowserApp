package uk.co.avsoftware.fragvm.blockchain.model

//@Entity(tableName = "blocks")
data class Block(
    var hash: String,
    var time: Long,
    var block_index: Int,
    var height: Int,
    //var tx_indexes: List<Long>
) {
    //@PrimaryKey(autoGenerate = true)
    var id: Int = 0
}