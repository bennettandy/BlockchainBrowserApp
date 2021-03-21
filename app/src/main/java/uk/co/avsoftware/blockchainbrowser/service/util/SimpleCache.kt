package uk.co.avsoftware.blockchainbrowser.service.util

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

class SimpleCache<T> @Inject constructor(private val clock: Clock, private val ttlMillis: Long = DEFAULT_TTL) {

    private val subject: BehaviorSubject<Pair<Instant,T>> = BehaviorSubject.create()

    fun onNext( value: T) = subject.onNext(Pair(clock.instant(), value))

    fun value(): Observable<T> = subject
        .filter { it.first.plusMillis(ttlMillis).isAfter(clock.instant()) }
        .map { it.second }
        .firstElement()
        .doOnSuccess { Timber.i("$it From Cache") }
        .toObservable()

    companion object {
        const val DEFAULT_TTL = 60000L
    }
}
//
//class MultiValueCache @Inject constructor(private val clock: Clock, private val ttlMillis: Long = SimpleCache.DEFAULT_TTL) {
//
//    private val map: ConcurrentMap<String, CacheEntry> = ConcurrentHashMap()
//
//    fun onNext( key: String, value: Any ) = map.put(key, CacheEntry(clock.instant(), value))
//
//    fun value(key: String, cla): Observable<Any>
//
//    data class CacheEntry(val timestamp: Instant, val entry: Any)
//
//}