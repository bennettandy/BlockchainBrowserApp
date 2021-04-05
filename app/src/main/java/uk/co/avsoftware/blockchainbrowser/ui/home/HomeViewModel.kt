package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import java.math.BigDecimal
import java.util.function.Consumer
import javax.inject.Inject
import javax.inject.Provider

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
            val result: Result<Stats> = kotlin.runCatching {  blockchainRepository.getGeneralStats() }
            result.exceptionOrNull()?.run { _errorMessage.postValue(message) }
            result.getOrNull()?.run { _stats.postValue(toString()) }
        }
    }

    private fun <T> runInCoroutine(
        deferred: Provider<T>,
        consumer: Consumer<T>,
        errorHandler: Consumer<Throwable>
    ) = try {
        viewModelScope.launch { consumer.accept(deferred.get()) }
    } catch (t: Throwable) {
        errorHandler.accept(t)
    }

}