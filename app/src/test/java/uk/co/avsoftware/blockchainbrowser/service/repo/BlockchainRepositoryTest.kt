package uk.co.avsoftware.blockchainbrowser.service.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainChartingApi
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainRestApi
import uk.co.avsoftware.blockchainbrowser.service.api.BlockchainSimpleQueryApi
import uk.co.avsoftware.blockchainbrowser.service.repo.impl.BlockChainRepositoryImpl
import java.util.concurrent.TimeUnit


class BlockchainRepositoryTest {

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        // set calls to Schedulers.computation() to use our test scheduler
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler}
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
    }

    @Test
    fun `successful hash rate call`() {

        val blockchainApi = Mockito.mock(BlockchainSimpleQueryApi::class.java)

        val restApi = Mockito.mock(BlockchainRestApi::class.java)

        val chartApi = Mockito.mock(BlockchainChartingApi::class.java)

        Mockito.`when`(blockchainApi.currentHashRateGigaHashes()).thenReturn(Single.just(209080L))

        val repository: BlockchainRepository = BlockChainRepositoryImpl(blockchainApi, restApi, chartApi)

        val testObserver: TestObserver<Long> = repository.currentHashRateGigaHashes().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(209080L)

    }

    @Test
    fun `time out hash rate call`() {

        val blockchainApi = Mockito.mock(BlockchainSimpleQueryApi::class.java)

        val restApi = Mockito.mock(BlockchainRestApi::class.java)

        val chartApi = Mockito.mock(BlockchainChartingApi::class.java)

        Mockito.`when`(blockchainApi.currentHashRateGigaHashes()).thenReturn(Single.never())

        val repository: BlockchainRepository = BlockChainRepositoryImpl(blockchainApi, restApi, chartApi)

        val testObserver: TestObserver<Long> = repository.currentHashRateGigaHashes().test()

        testObserver.assertNoErrors()

        testScheduler.advanceTimeBy(1, TimeUnit.MINUTES)

        testObserver.assertValue(0L)

    }
}