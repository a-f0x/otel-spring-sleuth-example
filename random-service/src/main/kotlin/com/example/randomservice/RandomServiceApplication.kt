package com.example.randomservice

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class RandomServiceApplication : CommandLineRunner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<RandomServiceApplication>(*args)
        }
    }

    override fun run(vararg args: String?) {

    }
}
