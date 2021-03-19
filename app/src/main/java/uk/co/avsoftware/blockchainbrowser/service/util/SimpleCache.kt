package uk.co.avsoftware.blockchainbrowser.service.util

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

class SimpleCache<T> @Inject constructor(private val clock: Clock, private val ttlMillis: Long = DEFAULT_TTL) {

    private val subject: BehaviorSubject<Pair<Instant,T>> = BehaviorSubject.create()

    fun onNext( value: T) = subject.onNext(Pair(clock.instant(), value))

    fun value(): Maybe<T> = subject
        .filter { it.first.plusMillis(ttlMillis).isAfter(clock.instant()) }
        .map { it.second }
        .firstElement()

    companion object {
        const val DEFAULT_TTL = 60000L
    }
}