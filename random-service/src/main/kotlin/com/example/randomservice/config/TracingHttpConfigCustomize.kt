package com.example.randomservice.config

import org.springframework.cloud.sleuth.Tracer
import org.springframework.cloud.sleuth.http.HttpRequestParser
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter
import javax.servlet.http.HttpServletResponse


/**
 * https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/howto.html#how-to-cutomize-http-server-spans
 * */

@Configuration(proxyBeanMethods = false)
class TracingHttpConfigCustomize {
    private companion object {
        private const val TRACE_ID_HEADER_KEY = "Trace-Id"
    }

    /**
     * Изменим имя спана входящих запросов по ресту к нашему сервиси
     * на нормальное название - МЕТОД ендпоинт
     *
     * GET /api/randomize
     * */
    @Bean(name = [HttpServerRequestParser.NAME])
    fun pathInSpanNameServerHttpRequestParser(): HttpRequestParser = HttpRequestParser { request, _, span ->
        span.name("${request.method()} ${request.path()}")
    }

    /**
     * Добавим во все рестовые ответы от нашего сервера заголовок с текущим trace id
     * Header = Trace-Id: 09weu8gfnjikogeqbvre0rio0
     * */
    @Bean
    fun traceIdInResponseFilter(tracer: Tracer): Filter = Filter { request, response, chain ->
        tracer.currentSpan()?.let {
            val resp = response as HttpServletResponse
            resp.addHeader(TRACE_ID_HEADER_KEY, it.context().traceId())
        }
        chain.doFilter(request, response)
    }
}
