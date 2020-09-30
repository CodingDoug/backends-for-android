package com.hyperaware.bfa.cloudfunctions

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse

@Suppress("unused")
class HelloWorld : HttpFunction {
    @Throws(Exception::class)
    override fun service(request: HttpRequest, response: HttpResponse) {
        response.writer.write("Hello, World 2\n")
    }
}
