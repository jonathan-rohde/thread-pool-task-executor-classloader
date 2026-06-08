package rohde.jonathan.threadpoolexample.lib.v1

import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import rohde.jonathan.threadpoolexample.lib.GreetingService

@Service
class GreetingServiceDefault(
    @param:Qualifier("riskyThreadPool") private val greetingExecutor: ThreadPoolTaskExecutor
) : GreetingService {

    companion object : KLogging()

    override fun doLog(c: Char) {
        greetingExecutor.execute { log(c) }
    }
}
