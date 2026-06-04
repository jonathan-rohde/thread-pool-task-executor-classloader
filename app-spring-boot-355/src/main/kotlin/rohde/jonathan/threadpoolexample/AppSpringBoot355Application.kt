package rohde.jonathan.threadpoolexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["rohde.jonathan.threadpoolexample"])
class AppSpringBoot355Application

fun main(args: Array<String>) {
	runApplication<AppSpringBoot355Application>(*args)
}
