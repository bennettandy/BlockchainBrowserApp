package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(blockchainRepository: BlockchainRepository) : ViewModel() {

    val hashRate: LiveData<Long> = LiveDataReactiveStreams.fromPublisher(
        blockchainRepository.currentHashRateGigaHashes().toFlowable()
    )
    val blockCount: LiveData<Long> =
        LiveDataReactiveStreams.fromPublisher(blockchainRepository.getBlockCount().toFlowable())
    val difficulty: LiveData<String> = LiveDataReactiveStreams.fromPublisher(
        blockchainRepository.getDifficulty()
            .map(BigDecimal::toString).toFlowable()
    )
    val latestHash: LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(blockchainRepository.getLatestHash().toFlowable())

    private val _stats = MutableLiveData<String>()
    val generalStats: LiveData<String> = _stats

    private val _errorMessage = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            val result: Stats = blockchainRepository.getGeneralStats().await()
            try {
                // seems messier than Rx Single
                result.run { _stats.postValue(toString()) }
            } catch (t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        }
    }

}