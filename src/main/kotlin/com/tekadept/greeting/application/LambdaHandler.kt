package com.tekadept.greeting.application

import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler


class LambdaHandler : RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private var handler: SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>? = null
    private var initialized = false

    override fun handleRequest(awsProxyRequest: AwsProxyRequest, context: Context): AwsProxyResponse? {
        if (!initialized) {
            try {
                handler = SpringLambdaContainerHandler.getAwsProxyHandler(MvcConfig::class.java)
                initialized = true
            } catch (e: ContainerInitializationException) {
                println("Unable to create handler: $e")
                return null
            }

        }
        return handler!!.proxy(awsProxyRequest, context)
    }
}
