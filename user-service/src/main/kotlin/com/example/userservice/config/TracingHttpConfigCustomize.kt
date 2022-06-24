package com.example.userservice.config

import org.springframework.cloud.sleuth.SpanCustomizer
import org.springframework.cloud.sleuth.TraceContext
import org.springframework.cloud.sleuth.Tracer
import org.springframework.cloud.sleuth.http.HttpRequest
import org.springframework.cloud.sleuth.http.HttpRequestParser
import org.springframework.cloud.sleuth.instrument.web.HttpClientRequestParser
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
     * GET /api/random
     * */
    @Bean(name = [HttpServerRequestParser.NAME])
    fun pathInSpanNameServerHttpRequestParser(): HttpRequestParser = HttpRequestParser { request, _, span ->
        span.name("${request.method()} ${request.path()}")
    }

    /**
     * Изменим имя спана исходящих запросов с нашего сервиса  по ресту
     * в другой сервис на нормальное название - МЕТОД урл
     *
     * GET http://localhost/randomize
     * */
    @Bean(name = [HttpClientRequestParser.NAME])
    fun pathInSpanNameClientRequestParser(): HttpRequestParser? {
        return HttpRequestParser { request: HttpRequest, _: TraceContext?, span: SpanCustomizer ->
            span.name("${request.method()} ${request.url()}")
        }
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
