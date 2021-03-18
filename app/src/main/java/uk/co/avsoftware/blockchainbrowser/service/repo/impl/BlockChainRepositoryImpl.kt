package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.repo.api.BlockchainApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(private val blockchainApi: BlockchainApi ) : BlockchainRepository {

    private val hashCache: BehaviorSubject<Long> = BehaviorSubject.createDefault(EMPTY)

    override fun currentHashRateGigaHashes(): Single<Long> = blockchainApi
        .currentHashRateGigaHashes()
        .timeout(2000, TimeUnit.MILLISECONDS)
        .doOnSuccess(hashCache::onNext)
        .concatWith { hashCache.filter { it > 0 } }
        .firstOrError()

    override fun flushCache() {
        hashCache.onNext(EMPTY)
    }

    companion object {
        private const val EMPTY = -1L
    }
}