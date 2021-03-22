package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val blockchainRepository: BlockchainRepository) : ViewModel() {

    val hashRate: LiveData<Long> = LiveDataReactiveStreams.fromPublisher(blockchainRepository.currentHashRateGigaHashes().toFlowable())
    val blockCount: LiveData<Long> = LiveDataReactiveStreams.fromPublisher(blockchainRepository.getBlockCount().toFlowable())
    val difficulty: LiveData<String> = LiveDataReactiveStreams.fromPublisher(blockchainRepository.getDifficulty()
        .map(BigDecimal::toString).toFlowable())
    val latestHash: LiveData<String> = LiveDataReactiveStreams.fromPublisher(blockchainRepository.getLatestHash().toFlowable())

    val generalStats: LiveData<String> = LiveDataReactiveStreams.fromPublisher(blockchainRepository.getGeneralStats().map(Stats::toString).toFlowable())
}