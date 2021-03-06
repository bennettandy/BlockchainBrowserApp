package uk.co.avsoftware.blockchainbrowser.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.avsoftware.blockchainbrowser.service.model.Block
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(blockchainRepository: BlockchainRepository) : ViewModel() {

    val latestBlock: LiveData<Block> = LiveDataReactiveStreams.fromPublisher( blockchainRepository.getLatestBlock()
        .doOnSubscribe { _progress.postValue(true) }
        .doOnTerminate {  _progress.postValue(false) }
        .toFlowable() )

    private val _progress = MutableLiveData(false)

    val progress: LiveData<Boolean> = _progress
}