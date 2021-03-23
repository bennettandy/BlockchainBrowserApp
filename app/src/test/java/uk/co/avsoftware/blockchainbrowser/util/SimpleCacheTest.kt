package uk.co.avsoftware.blockchainbrowser.util

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.threeten.extra.MutableClock
import uk.co.avsoftware.blockchainbrowser.service.util.SimpleCache
import java.time.Instant
import java.time.ZoneId

class SimpleCacheTest {

    @get:Rule
    var rule: TestRule = RxImmediateSchedulerRule()

    private val clock = MutableClock.of(Instant.now(), ZoneId.systemDefault())

    @Test
    fun testHappyPath(){

        // Given
        val cache = SimpleCache<String>(clock)
        val testObserver = cache.value().test()

        // When
        cache.onNext("My Stored Value")

        // Then
        testObserver.assertValue("My Stored Value")
        testObserver.assertComplete()
    }

    @Test
    fun testReplaceValue(){

        // Given
        val cache = SimpleCache<String>(clock)

        // When
        cache.onNext("My Old Stored Value")

        cache.onNext("My New Stored Value")

        val testObserver = cache.value().test()

        // Then
        testObserver.assertValue("My New Stored Value")
        testObserver.assertComplete()
    }

    @Test
    fun testEmptyCache(){

        // Given
        val cache = SimpleCache<String>(clock)
        val testObserver = cache.value().test()

        // Then
        testObserver.assertEmpty()
    }

}