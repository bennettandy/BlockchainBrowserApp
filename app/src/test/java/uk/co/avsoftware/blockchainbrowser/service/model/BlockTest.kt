package uk.co.avsoftware.blockchainbrowser.service.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Test
import uk.co.avsoftware.fragvm.blockchain.model.Block
import uk.co.avsoftware.fragvm.blockchain.model.TransactionInput
import uk.co.avsoftware.fragvm.blockchain.model.TransactionOutput
import java.io.File

class BlockTest {

    @Test
    fun `deserialise Block from json`(){

        val jsonText: String = File("./src/test/res/raw/block.json").readText(Charsets.UTF_8)

        val gson: Gson = GsonBuilder().create()

        val block: Block = gson.fromJson(jsonText, Block::class.java)

        assertThat(block.hash).isEqualTo("0000000000000000000a2a0731e79f977c689e4f3bf0a72eec99abe3c3478e52")
        assertThat(block.previousBlock).isEqualTo("000000000000000000004b9f2a0de049afb1c20466db3cc4215424bcbe2a45ff")
        assertThat(block.merkleRoot).isEqualTo("8213ad65aa1a300c273b153f342b0f627ed1559f4aa21f8bbf7b91651eef2981")
        assertThat(block.tx).hasSize(1902)

        // transaction 0 - Coinbase - // https://learnmeabitcoin.com/technical/coinbase-transaction
        val transaction = block.tx[0]
        assertThat(transaction.block_height).isEqualTo(675759)
        assertThat(transaction.hash).isEqualTo("83fb726c688d2b07cd4e45a974c74e282ebdd1d51e1bfde73e6d20ccc47b7b02")
        assertThat(transaction.inputs).hasSize(1)
        assertThat(transaction.out).hasSize(4)

        // input 0
        val input: TransactionInput = transaction.inputs[0]
        assertThat(input.sequence).isEqualTo(4294967295)
        assertThat(input.index).isEqualTo(0)
        assertThat(input.witness).isEqualTo("0000000000000000000000000000000000000000000000000000000000000000")
        assertThat(input.script).isEqualTo("03af4f0a04e57358602f706f6f6c696e2e636f6d2f746170726f6f742f626970392f13fb04220b267f7579ba48567a5fbc431168a40df5001ed6010000000000")

        // output 0
        val output: TransactionOutput = transaction.out[0]
        assertThat(output.spent).isFalse()
        assertThat(output.value).isEqualTo(641483202)
        assertThat(output.spending_outpoints).isEmpty()
        /*
        "type": 0,
          "spent": false,
          "value": 641483202,
          "spending_outpoints": [],
          "addr": "191sNkKTG8pzUsNgZYKo7DH2odg39XDAGo",
          "n": 0,
          "tx_index": 0,
          "script": "76a91457eb0ea1de7bd9b63d59c29d60941adb61c597cf88ac"
         */

    }
}