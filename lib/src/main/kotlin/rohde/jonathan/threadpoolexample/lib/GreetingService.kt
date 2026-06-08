package rohde.jonathan.threadpoolexample.lib

import jakarta.xml.bind.JAXBContextFactory
import mu.KLogging
import java.util.ServiceLoader.load

interface GreetingService {

    companion object : KLogging()

    fun log(c: Char) {
        val factory = load(JAXBContextFactory::class.java)
            .findFirst()
            .orElseThrow { IllegalStateException("No JAXBContextFactory provider found") }
        logger.info { "[${Thread.currentThread().name}] loaded ${factory::class.java.name} | char=$c" }
    }

    fun doLog(c: Char)
}
