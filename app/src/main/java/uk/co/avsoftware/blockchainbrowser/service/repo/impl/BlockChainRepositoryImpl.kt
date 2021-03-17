package uk.co.avsoftware.blockchainbrowser.service.repo.impl

import io.reactivex.rxjava3.core.Single
import uk.co.avsoftware.blockchainbrowser.service.repo.BlockchainRepository
import uk.co.avsoftware.blockchainbrowser.service.repo.api.BlockchainApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlockChainRepositoryImpl @Inject constructor(private val blockchainApi: BlockchainApi ) : BlockchainRepository {

    override fun currentHashRateGigaHashes(): Single<Long> = blockchainApi
        .currentHashRateGigaHashes()
        .timeout(2000, TimeUnit.MILLISECONDS)

}