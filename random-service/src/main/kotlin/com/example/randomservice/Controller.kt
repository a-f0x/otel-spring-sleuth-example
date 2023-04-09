package com.example.randomservice

import com.example.randomservice.dto.ErrorDTO
import com.example.randomservice.dto.RandomDTO
import io.micrometer.tracing.Tracer
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
class Controller(private val randomService: RandomService) {
    private val logger = LoggerFactory.getLogger(Controller::class.java)

    @Operation(summary = "Get random number")
    @GetMapping(path = ["/randomize"])
    fun randomize(@RequestHeader headers: MultiValueMap<String, String>): ResponseEntity<*> {
        headers.forEach {
            logger.info("Header %s=%s".format(it.key, it.value))
        }


        return ResponseEntity.ok(RandomDTO(randomService.random()))
    }

}

@ControllerAdvice
class MvcErrorHandler(
    private val tracer: Tracer
) : ResponseEntityExceptionHandler() {

    private val l = LoggerFactory.getLogger(MvcErrorHandler::class.java)

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return onError(ex, statusCode)
    }

    @ExceptionHandler(Throwable::class)
    fun handleThrowable(t: Throwable, request: WebRequest): ResponseEntity<Any> =
        onError(t, HttpStatus.INTERNAL_SERVER_ERROR)

    /**
     * Добавим в тело ответа с ошибкой текущий трейс ид для удобства клиентов
     * */
    fun onError(t: Throwable, status: HttpStatusCode): ResponseEntity<Any> = with(t) {
        l.error("error", t)
        ResponseEntity(
            ErrorDTO(
                tracer.currentSpan()?.context()?.traceId(),
                t.message ?: "Error"
            ), status
        )
    }
}


