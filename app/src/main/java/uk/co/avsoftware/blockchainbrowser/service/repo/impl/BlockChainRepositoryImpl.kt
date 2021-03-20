package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.repo.api.BlockchainApi
import uk.co.avsoftware.blockchainbrowser.service.util.SimpleCache
import java.time.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(private val blockchainApi: BlockchainApi, clock: Clock = Clock.systemDefaultZone()) :
    BlockchainRepository {

    private val simpleCache: SimpleCache<Long> = SimpleCache(clock)

    override fun currentHashRateGigaHashes(): Single<Long> = simpleCache.value()
        .doOnSuccess { Timber.i("$it From Cache") }
        .toObservable()
        .mergeWith(blockchainApi.currentHashRateGigaHashes()
            .doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(simpleCache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e(it, "Error getting current Hash Rate from API, %s", it.message) }

}