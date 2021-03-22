package uk.co.avsoftware.blockchainbrowser.service.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Test
import uk.co.avsoftware.fragvm.blockchain.model.Block
import java.io.File

class BlockTest {

    @Test
    fun `deserialise Block from json`(){

        val jsonText: String = File("./src/test/res/raw/block.json").readText(Charsets.UTF_8)

        val gson: Gson = GsonBuilder().create()

        val block: Block = gson.fromJson(jsonText, Block::class.java)

        assertThat(block.hash).isEqualTo("0000000000000000000a2a0731e79f977c689e4f3bf0a72eec99abe3c3478e52")
    }
}