package org.example.lambda1

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.log4j.BasicConfigurator
import org.slf4j.LoggerFactory
import spark.Spark.get
import spark.Spark.initExceptionHandler
import kotlin.jvm.Throws

class LambdaHandler @Throws(ContainerInitializationException::class)

constructor(): RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private val handler =SparkLambdaContainerHandler.getAwsProxyHandler()
    private var initialized = false
    private val log = LoggerFactory.getLogger(LambdaHandler::class.java)

    override fun handleRequest(awsProxyRequest: AwsProxyRequest, context:Context?): AwsProxyResponse {
        if (!initialized) {
            defineRoute()
            initialized = true
        }
        return handler.proxy(awsProxyRequest, context)
    }

    private fun defineRoute() {
        BasicConfigurator.configure()
        initExceptionHandler{e -> log.error("Spark init failure")
            System.exit(100)
        }
        get("/hello") {_, _ -> "Hello world"}
    }
}

