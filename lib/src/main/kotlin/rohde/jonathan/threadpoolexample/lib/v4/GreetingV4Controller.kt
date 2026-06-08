package rohde.jonathan.threadpoolexample.lib.v4

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greeting/v4")
class GreetingV4Controller(
    private val greetingService: GreetingServiceWithClassloaderOverride
) {

    @GetMapping
    fun greet(@RequestParam(defaultValue = "World") name: String): List<Char> {
        val chars = name.toList()
        chars.forEach { greetingService.doLog(it) }
        return chars
    }
}
