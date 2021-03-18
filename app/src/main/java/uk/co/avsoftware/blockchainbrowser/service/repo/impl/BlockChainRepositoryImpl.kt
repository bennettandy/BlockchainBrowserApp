package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.repo.api.BlockchainApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(private val blockchainApi: BlockchainApi) :
    BlockchainRepository {

    private val hashCache: BehaviorSubject<Long> = BehaviorSubject.createDefault(EMPTY)

    override fun currentHashRateGigaHashes(): Single<Long> = hashCache
        .take(1)
        .filter { it > 0 }
        .doOnNext { Timber.i("$it From Cache") }
        .mergeWith(blockchainApi.currentHashRateGigaHashes()
            .doOnSuccess { Timber.i("$it From API") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(hashCache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e(it, "Error getting current Hash Rate from API, %s", it.message) }

    override fun flushCache() {
        hashCache.onNext(EMPTY)
    }

    companion object {
        private const val EMPTY = -1L
    }
}