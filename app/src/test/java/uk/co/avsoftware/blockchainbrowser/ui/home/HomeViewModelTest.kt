package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import java.util.concurrent.TimeUnit

class HomeViewModelTest {

    // Makes LiveData posts synchronous or would not complete until after test completion
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    // looking redundant in this case
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

        // Given
        val blockchainRepository = Mockito.mock(BlockchainRepository::class.java)

        Mockito.`when`(blockchainRepository.currentHashRateGigaHashes()).thenReturn(Single.just(209080L))

        val viewModel: HomeViewModel = HomeViewModel(blockchainRepository)

        //testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        assertThat(viewModel.hashRate.observe(1), Matchers.`is`(209080L))


    }

    fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }
}