package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import io.reactivex.rxjava3.core.Single
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.util.mock
import java.math.BigDecimal

class HomeViewModelTest {

    // Makes LiveData posts synchronous or would not complete until after test completion
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `successful hash rate call`() {

        // Given
        val blockchainRepository: BlockchainRepository = mock()

        Mockito.`when`(blockchainRepository.currentHashRateGigaHashes()).thenReturn(Single.just(209080L))
        Mockito.`when`(blockchainRepository.getBlockCount()).thenReturn(Single.just(1000L))
        Mockito.`when`(blockchainRepository.getDifficulty()).thenReturn(Single.just(BigDecimal.ONE))
        Mockito.`when`(blockchainRepository.getLatestHash()).thenReturn(Single.just("000000dsdbfdhavfdvfkf"))

        // When
        val viewModel = HomeViewModel(blockchainRepository)

        // Then
        assertLiveData(viewModel.hashRate, 209080L)
        assertLiveData(viewModel.blockCount, 1000L)
        assertLiveData(viewModel.difficulty, BigDecimal.ONE.toString())
        assertLiveData(viewModel.latestHash, "000000dsdbfdhavfdvfkf")
    }

    private fun <T> assertLiveData( liveData: LiveData<T>, expectedValue: T) =
        liveData.observeForever {
            assertThat(it, Matchers.`is`(expectedValue))
        }

}