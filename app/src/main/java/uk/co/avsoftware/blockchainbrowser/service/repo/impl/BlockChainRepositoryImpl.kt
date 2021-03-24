package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred
import timber.log.Timber
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainChartingApi
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainRestApi
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainSimpleQueryApi
import uk.co.avsoftware.blockchainbrowser.service.model.Block
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.blockchainbrowser.service.model.Transaction
import uk.co.avsoftware.blockchainbrowser.service.util.SimpleCache
import java.math.BigDecimal
import java.time.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(
    private val simpleQueryApi: BlockchainSimpleQueryApi,
    private val restApi: BlockchainRestApi,
    private val chartingApi: BlockchainChartingApi,
    clock: Clock = Clock.systemDefaultZone()
) :
    BlockchainRepository {

    private val hashRateCache: SimpleCache<Long> = SimpleCache(clock)
    private val blockCountCache: SimpleCache<Long> = SimpleCache(clock)
    private val difficultyCache: SimpleCache<BigDecimal> = SimpleCache(clock)
    private val latestHashCache: SimpleCache<String> = SimpleCache(clock)
    private val statsCache: SimpleCache<Stats> = SimpleCache(clock)
    private val blockCache: SimpleCache<Block> = SimpleCache(clock)

    // cached functions
    override fun currentHashRateGigaHashes(): Single<Long> =
        cachedApiCall(hashRateCache, simpleQueryApi.currentHashRateGigaHashes(), 0L)

    override fun getBlockCount(): Single<Long> =
        cachedApiCall(blockCountCache, simpleQueryApi.getBlockCount(), 0L)

    override fun getDifficulty(): Single<BigDecimal> =
        cachedApiCall(difficultyCache, simpleQueryApi.getDifficulty(), BigDecimal.ZERO)

    override fun getLatestHash(): Single<String> =
        cachedApiCall(latestHashCache, simpleQueryApi.getLatestHash(), "???")

    // adding caching temporarily as this is a heavy call
    override fun getLatestBlock(): Single<Block> =
        cachedApiCall(blockCache, restApi.getLatestBlock()
            .map(Block::hash)
            .doOnSuccess { Timber.i("Block Hash: $it") }
            .flatMap {
                restApi.getRawBlockByHash(it)
                    .doOnEvent { t1, t2 -> Timber.i("$t1, $t2") }
            }, Block.tempTest()
        )

    override fun getTransactionByHash(txHash: String): Single<Transaction> =
        restApi.getTransactionByHash(txHash)

    // coroutine call
    override fun getGeneralStatsAsync(): Deferred<Stats> = chartingApi.getStatsAsync()
        //cachedApiCall(statsCache, chartingApi.getStats(), Stats())


    private fun <T> cachedApiCall(
        cache: SimpleCache<T>,
        apiCall: Single<T>,
        errorItem: T
    ): Single<T> = cache.value()
        .mergeWith(apiCall.doOnSuccess { Timber.i("$it From API") }
            .doOnEvent { t1, t2 -> Timber.i("$t1, $t2") }
            .timeout(5000, TimeUnit.MILLISECONDS)
            .doOnSuccess(cache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e("Api error $it") }
        .onErrorReturnItem(errorItem)
}