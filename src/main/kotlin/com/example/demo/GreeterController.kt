package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("greeter")
// コンストラクタインジェクション（推奨）
class GreeterController(private val greeter: Greeter, @Qualifier("EnglishMessageService") private val messageService: MessageService) {
//class GreeterController {
    // フィールドインジェクション
//    @Autowired
//    private lateinit var greeter: Greeter

    // セッターインジェクション
//    var greeter: Greeter? = null
//    @Autowired
//    set(value) {
//        field = value
//    }

    @GetMapping("/hello")
    fun hello(@RequestParam("name") name: String): HelloResponse {
        return HelloResponse("Hello $name")
    }

    @GetMapping("/hello/{name}")
    fun helloPathValue(@PathVariable("name") name: String): HelloResponse {
        return HelloResponse("hello $name")
    }

    @PostMapping("/hello")
    fun helloByPost(@RequestBody request: HelloRequest): HelloResponse {
        return HelloResponse("hello ${request.name}")
    }

    @GetMapping("/hello/byservice/{name}")
    fun helloByService(@PathVariable("name") name: String): HelloResponse {
        val message = greeter.sayHello(name)
        return HelloResponse(message)
    }
}