package rohde.jonathan.threadpoolexample.lib

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greeting/v3")
class GreetingV3Controller(private val greetingService: GreetingService) {

    @GetMapping
    fun greet(@RequestParam(defaultValue = "World") name: String): List<Char> {
        val chars = name.toList()
        chars.forEach { greetingService.logAsyncImproved(it) }
        return chars
    }
}
