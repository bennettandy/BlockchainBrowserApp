package uk.co.avsoftware.blockchainbrowser.util

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxImmediateSchedulerRule : TestRule {

    private val trampolineScheduler = Schedulers.trampoline()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { trampolineScheduler }
                RxJavaPlugins.setInitComputationSchedulerHandler { trampolineScheduler }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { trampolineScheduler }
                RxJavaPlugins.setInitSingleSchedulerHandler { trampolineScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { trampolineScheduler }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}