package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainApi
import uk.co.avsoftware.blockchainbrowser.service.util.SimpleCache
import java.math.BigDecimal
import java.time.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(private val blockchainApi: BlockchainApi, clock: Clock = Clock.systemDefaultZone()) :
    BlockchainRepository {

    private val hashRateCache: SimpleCache<Long> = SimpleCache(clock)
    private val blockCountCache: SimpleCache<Long> = SimpleCache(clock)
    private val difficultyCache: SimpleCache<BigDecimal> = SimpleCache(clock)
    private val latestHashCache: SimpleCache<String> = SimpleCache(clock)

    override fun currentHashRateGigaHashes(): Single<Long> = cachedApiCall(hashRateCache, blockchainApi.currentHashRateGigaHashes(), 0L)
    override fun getBlockCount(): Single<Long> = cachedApiCall(blockCountCache, blockchainApi.getBlockCount(), 0L)
    override fun getDifficulty(): Single<BigDecimal> = cachedApiCall(difficultyCache, blockchainApi.getDifficulty(), BigDecimal.ZERO)
    override fun getLatestHash(): Single<String> = cachedApiCall(latestHashCache, blockchainApi.getLatestHash(), "???")

    private fun <T> cachedApiCall(cache: SimpleCache<T>, apiCall: Single<T>, errorItem: T): Single<T> = cache.value()
        .mergeWith(apiCall.doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(cache::onNext)
        )
        .firstOrError()
        .onErrorReturnItem(errorItem)
}