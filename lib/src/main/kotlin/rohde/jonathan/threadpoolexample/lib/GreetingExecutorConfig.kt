package rohde.jonathan.threadpoolexample.lib

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator
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

    @Bean("greetingExecutorImprove")
    open fun greetingExecutorImprove(): ThreadPoolTaskExecutor {
        val threadCount = AtomicInteger(0)
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 10
            maxPoolSize = 20
            setThreadFactory { runnable ->
                Thread(runnable, "greeting-executor-${threadCount.incrementAndGet()}").also {
                    logger.info { "Thread '${it.name}' created, contextClassLoader=${it.contextClassLoader}" }
                }
            }
            setTaskDecorator(Decorator())
            initialize()
        }
    }
}

class Decorator: TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        val contextClassLoader = Thread.currentThread().getContextClassLoader()
        return Runnable {
            val currentThread = Thread.currentThread()
            val originalClassLoader = currentThread.getContextClassLoader()
            try {
                currentThread.setContextClassLoader(contextClassLoader)
                runnable.run()
            } finally {
                currentThread.setContextClassLoader(originalClassLoader)
            }
        }
    }

}
