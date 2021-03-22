package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Consumer
import timber.log.Timber
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainChartingApi
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainRestApi
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainSimpleQueryApi
import uk.co.avsoftware.blockchainbrowser.service.model.Stats
import uk.co.avsoftware.blockchainbrowser.service.util.SimpleCache
import uk.co.avsoftware.fragvm.blockchain.model.Block
import uk.co.avsoftware.fragvm.blockchain.model.Transaction
import java.math.BigDecimal
import java.time.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(private val simpleQueryApi: BlockchainSimpleQueryApi,
                                                   private val restApi: BlockchainRestApi,
                                                   private val chartingApi: BlockchainChartingApi,
                                                   clock: Clock = Clock.systemDefaultZone()) :
    BlockchainRepository {

    private val hashRateCache: SimpleCache<Long> = SimpleCache(clock)
    private val blockCountCache: SimpleCache<Long> = SimpleCache(clock)
    private val difficultyCache: SimpleCache<BigDecimal> = SimpleCache(clock)
    private val latestHashCache: SimpleCache<String> = SimpleCache(clock)
    private val statsCache: SimpleCache<Stats> = SimpleCache(clock)

    // cached functions
    override fun currentHashRateGigaHashes(): Single<Long> = cachedApiCall(hashRateCache, simpleQueryApi.currentHashRateGigaHashes(), 0L)
    override fun getBlockCount(): Single<Long> = cachedApiCall(blockCountCache, simpleQueryApi.getBlockCount(), 0L)
    override fun getDifficulty(): Single<BigDecimal> = cachedApiCall(difficultyCache, simpleQueryApi.getDifficulty(), BigDecimal.ZERO)
    override fun getLatestHash(): Single<String> = cachedApiCall(latestHashCache, simpleQueryApi.getLatestHash(), "???")

    override fun getLatestBlock(): Single<Block> = restApi.getLatestBlock()
    override fun getTransactionByHash(tx_hash: String): Single<Transaction> = restApi.getTransactionByHash(tx_hash)

    override fun getGeneralStats(): Single<Stats> = cachedApiCall(statsCache, chartingApi.getStats(), Stats())


    private fun <T> cachedApiCall(cache: SimpleCache<T>, apiCall: Single<T>, errorItem: T): Single<T> = cache.value()
        .mergeWith(apiCall.doOnSuccess { Timber.i("$it From API") }
            .doOnEvent { t1, t2 -> Timber.i("$t1, $t2") }
            .timeout(2000, TimeUnit.MILLISECONDS)
            .doOnSuccess(cache::onNext)
        )
        .firstOrError()
        .doOnError { Timber.e("Api error $it") }
        .onErrorReturnItem(errorItem)
}