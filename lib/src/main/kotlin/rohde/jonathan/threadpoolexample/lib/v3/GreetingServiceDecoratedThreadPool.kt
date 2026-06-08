package rohde.jonathan.threadpoolexample.lib.v3

import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import rohde.jonathan.threadpoolexample.lib.GreetingService

@Service
class GreetingServiceDecoratedThreadPool(
    @param:Qualifier("decoratedThreadPool") private val greetingExecutorImprove: ThreadPoolTaskExecutor
) : GreetingService {

    companion object : KLogging()

    override fun doLog(c: Char) {
        greetingExecutorImprove.execute { log(c) }
    }
}
