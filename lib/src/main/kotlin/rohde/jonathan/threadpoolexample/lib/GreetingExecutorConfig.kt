package rohde.jonathan.threadpoolexample.lib

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.atomic.AtomicInteger

@Configuration
open class GreetingExecutorConfig {

    companion object : KLogging()

    @Bean
    open fun greetingExecutor(): ThreadPoolTaskExecutor {
        val threadCount = AtomicInteger(0)
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 10
            maxPoolSize = 20
            setThreadFactory { runnable ->
                Thread(runnable, "greeting-executor-${threadCount.incrementAndGet()}").also {
                    logger.info { "Thread '${it.name}' created, contextClassLoader=${it.contextClassLoader}" }
                }
            }
            initialize()
        }
    }
}
