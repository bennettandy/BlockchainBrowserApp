package uk.co.avsoftware.blockchainbrowser.service.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import org.junit.Test
import uk.co.avsoftware.fragvm.blockchain.model.Block
import java.io.File

class BlockTest {

    @Test
    fun `deserialise Block from json`() {

        // https://blockchain.info/rawblock/0000000000000000000a2a0731e79f977c689e4f3bf0a72eec99abe3c3478e52

        blockFromJson("./src/test/res/raw/block.json").apply {
            assertThat(hash).isEqualTo("0000000000000000000a2a0731e79f977c689e4f3bf0a72eec99abe3c3478e52")
            assertThat(previousBlock).isEqualTo("000000000000000000004b9f2a0de049afb1c20466db3cc4215424bcbe2a45ff")
            assertThat(merkleRoot).isEqualTo("8213ad65aa1a300c273b153f342b0f627ed1559f4aa21f8bbf7b91651eef2981")
            assertThat(tx).hasSize(1902)

            // transaction 0 - Coinbase - // https://learnmeabitcoin.com/technical/coinbase-transaction
            this.tx[0].apply {
                assertThat(block_height).isEqualTo(675759)
                assertThat(hash).isEqualTo("83fb726c688d2b07cd4e45a974c74e282ebdd1d51e1bfde73e6d20ccc47b7b02")
                assertThat(inputs).hasSize(1)
                assertThat(out).hasSize(4)

                // input 0
                this.inputs[0].apply {
                    assertThat(sequence).isEqualTo(4294967295)
                    assertThat(index).isEqualTo(0)
                    assertThat(witness).isEqualTo("0000000000000000000000000000000000000000000000000000000000000000")
                    assertThat(script).isEqualTo("03af4f0a04e57358602f706f6f6c696e2e636f6d2f746170726f6f742f626970392f13fb04220b267f7579ba48567a5fbc431168a40df5001ed6010000000000")
                }

                // output 0
                this.out[0].apply {
                    assertThat(spent).isFalse()
                    assertThat(value).isEqualTo(641483202)
                    assertThat(spending_outpoints).isEmpty()
                    assertThat(addr).isEqualTo("191sNkKTG8pzUsNgZYKo7DH2odg39XDAGo")
                    assertThat(n).isEqualTo(0)
                    assertThat(script).isEqualTo("76a91457eb0ea1de7bd9b63d59c29d60941adb61c597cf88ac")
                }
            }

            // transaction 1
            this.tx[1].apply {
                assertThat(hash).isEqualTo("3618b1bab1d5b1144edc3a8400ac3bd7eacb0f26fbbb4855736e8160d9c728aa")

                val outpoint = inputs[0].prev_out.spending_outpoints[0]
                outpoint.apply {
                    assertThat(tx_index).isEqualTo(0)
                    assertThat(n).isEqualTo(0)
                }
            }
        }
    }

    private fun blockFromJson(location: String): Block =
        File(location).readText(Charsets.UTF_8)
            .let {
                GsonBuilder().create().run {
                    fromJson(it, Block::class.java)
                }
            }
}