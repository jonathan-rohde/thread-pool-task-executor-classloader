package rohde.jonathan.threadpoolexample.lib.v4

import jakarta.xml.bind.JAXBContextFactory
import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import rohde.jonathan.threadpoolexample.lib.GreetingService

@Service
class GreetingServiceWithClassloaderOverride(
    @param:Qualifier("riskyThreadPool") private val greetingExecutor: ThreadPoolTaskExecutor
) : GreetingService {

    companion object : KLogging()

    override fun doLog(c: Char) {
        greetingExecutor.execute {
            val originalClassLoader = Thread.currentThread().getContextClassLoader()
            Thread.currentThread().contextClassLoader = JAXBContextFactory::class.java.classLoader
            log(c)
            Thread.currentThread().contextClassLoader = originalClassLoader
        }
    }
}
