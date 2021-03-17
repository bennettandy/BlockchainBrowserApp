package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val blockchainRepository: BlockchainRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "some text"
    }
    val text: LiveData<String> = _text
}