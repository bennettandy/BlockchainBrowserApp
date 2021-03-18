package uk.co.avsoftware.blockchainbrowser.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import io.reactivex.rxjava3.core.Single
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.util.mock

class HomeViewModelTest {

    // Makes LiveData posts synchronous or would not complete until after test completion
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `successful hash rate call`() {

        // Given
        val blockchainRepository: BlockchainRepository = mock()

        Mockito.`when`(blockchainRepository.currentHashRateGigaHashes()).thenReturn(Single.just(209080L))

        val viewModel = HomeViewModel(blockchainRepository)

        viewModel.hashRate.observeForever {
            assertThat(it, Matchers.`is`(209080L))
        }

    }

}