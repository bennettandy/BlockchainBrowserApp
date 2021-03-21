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

    // todo: extend cache to cache multiple values
    private val hashRateCache: SimpleCache<Long> = SimpleCache(clock)
    private val blockCountCache: SimpleCache<Long> = SimpleCache(clock)
    private val difficultyCache: SimpleCache<BigDecimal> = SimpleCache(clock)
    private val latestHashCache: SimpleCache<String> = SimpleCache(clock)

    // todo: move this common logic into a function
    override fun currentHashRateGigaHashes(): Single<Long> = hashRateCache.value()
        .mergeWith(blockchainApi.currentHashRateGigaHashes()
            .doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(hashRateCache::onNext) // needs encapsulating to remove this potential error opportunity
        )
        .firstOrError()
        .doOnError { Timber.e(it, "Error getting current Hash Rate from API, %s", it.message) }

    override fun getBlockCount(): Single<Long> = blockCountCache.value()
        .mergeWith(blockchainApi.getBlockCount()
            .doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(blockCountCache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e(it, "Error getting current Block Count from API, %s", it.message) }

    override fun getDifficulty(): Single<BigDecimal> = difficultyCache.value()
        .mergeWith(blockchainApi.getDifficulty()
            .doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(difficultyCache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e(it, "Error getting current Difficulty from API, %s", it.message) }

    override fun getLatestHash(): Single<String> = latestHashCache.value()
        .mergeWith(blockchainApi.getLatestHash()
            .doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(latestHashCache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e(it, "Error getting Latest Hash from API, %s", it.message) }
}