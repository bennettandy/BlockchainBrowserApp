package uk.co.avsoftware.blockchainbrowser.service.util

//import io.reactivex.rxjava3.core.Observable
//import io.reactivex.rxjava3.subjects.BehaviorSubject
//import timber.log.Timber
//import java.time.Clock
//import java.time.Instant

//class SimpleCache<T> (private val clock: Clock, private val ttlMillis: Long = DEFAULT_TTL) {
//
//    private val subject: BehaviorSubject<Pair<Instant,T>> = BehaviorSubject.create()
//
//    fun onNext( value: T) = subject.onNext(Pair(clock.instant(), value))
//
//    fun value(): Observable<T> = subject
//        .filter { it.first.plusMillis(ttlMillis).isAfter(clock.instant()) }
//        .map { it.second }
//        .firstElement()
//        .doOnSuccess { Timber.i("$it From Cache") }
//        .toObservable()
//
//    companion object {
//        const val DEFAULT_TTL = 60000L
//    }
//}