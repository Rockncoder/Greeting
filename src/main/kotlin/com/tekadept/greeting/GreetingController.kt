package com.tekadept.greeting

import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong



@RestController
@RequestMapping("/greeting")
class GreetingController {

    // When running under AWS Lambda, counter will always be 1
    val counter = AtomicLong()

    @GetMapping("/")
    fun greetings(@RequestParam(value = "name", defaultValue = "World")name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/{name}")
    fun greeting(@PathVariable name: String): Greeting
    {
        return Greeting(counter.incrementAndGet(), "Greetings, $name")
    }

    @PostMapping("/")
    fun postGreeting(): Unit {
        println("Pretend a post occurred.")
    }

}