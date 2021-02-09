package org.example.lambda1

import spark.Spark.get
import org.apache.log4j.BasicConfigurator

fun main(args: Array<String>) {
    BasicConfigurator.configure()
    get("/hello") { req, res -> "Hello world" }
    // http://localhost:4567/hello
}
