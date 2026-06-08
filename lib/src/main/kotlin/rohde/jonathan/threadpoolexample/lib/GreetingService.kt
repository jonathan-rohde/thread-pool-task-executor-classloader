package rohde.jonathan.threadpoolexample.lib

import jakarta.xml.bind.JAXBContextFactory
import mu.KLogging
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.ServiceLoader
import java.util.ServiceLoader.load

@Service
class GreetingService(private val greetingExecutor: ThreadPoolTaskExecutor,
                      private val greetingExecutorImprove: ThreadPoolTaskExecutor) {

    companion object : KLogging()

    fun logSync(c: Char) {
        val factory = ServiceLoader.load(JAXBContextFactory::class.java)
            .findFirst()
            .orElseThrow { IllegalStateException("No JAXBContextFactory provider found") }
        logger.info { "[${Thread.currentThread().name}] loaded ${factory::class.java.name} | char=$c" }
    }

    fun logAsync(c: Char) {
        greetingExecutor.execute { logSync(c) }
    }

    fun logAsyncImproved(c: Char) {
        greetingExecutorImprove.execute { logSync(c) }
    }

    fun logAsyncWithSpecificClassloader(c: Char) {
        greetingExecutorImprove.execute {
            val originalClassLoader = Thread.currentThread().getContextClassLoader()
            Thread.currentThread().contextClassLoader = JAXBContextFactory::class.java.classLoader
            val factory = load(JAXBContextFactory::class.java)
                .findFirst()
                .orElseThrow { IllegalStateException("No JAXBContextFactory provider found") }
            logger.info { "[${Thread.currentThread().name}] loaded ${factory::class.java.name} | char=$c" }
            Thread.currentThread().contextClassLoader = originalClassLoader
        }
    }
}
