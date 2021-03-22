package uk.co.avsoftware.blockchainbrowser.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction
import uk.co.avsoftware.fragvm.blockchain.model.Block

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _block = MutableLiveData<Block>().apply {
        value = Block("hash", 0,0,0,"0000123","bfdbsfa",
            listOf(Transaction("dfsdfasdfdfdfad", 0, 0, 0, "",0, "", 0, "", emptyList(), emptyList())))
    }
    val latestBlock: LiveData<Block> = _block
}