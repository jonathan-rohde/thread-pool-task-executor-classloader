package rohde.jonathan.threadpoolexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["rohde.jonathan.threadpoolexample"])
class AppSpringBoot406Application

fun main(args: Array<String>) {
	runApplication<AppSpringBoot406Application>(*args)
}
