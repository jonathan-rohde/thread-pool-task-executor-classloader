package rohde.jonathan.threadpoolexample.lib

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greeting/v2")
class GreetingV2Controller(private val greetingService: GreetingService) {

    @GetMapping
    fun greet(@RequestParam(defaultValue = "World") name: String): List<Char> {
        val chars = name.toList()
        chars.parallelStream().forEach { greetingService.logAsync(it) }
        return chars
    }
}
