package com.example.userservice


import com.example.userservice.dto.ErrorDTO
import com.example.userservice.dto.UserDTO
import com.example.userservice.service.RandomService
import com.example.userservice.service.UserService
import io.micrometer.tracing.Tracer
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
class Controller(
    private val service: UserService,
    private val randomService: RandomService,
) {
    @Operation(summary = "Create user and send notification to kafka")
    @PostMapping(path = ["/user"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person: UserDTO): ResponseEntity<Int> = ResponseEntity.ok(service.createPerson(person))

    @Operation(summary = "Trace repository layer")
    @GetMapping(path = ["/search"])
    fun getAll(
        @Parameter(name = "firstName", example = "string")
        @RequestParam("firstName")
        firstName: String
    ): ResponseEntity<Any> =
        ResponseEntity.ok(service.search(firstName))

    @Operation(summary = "Get random int from other service")
    @GetMapping(path = ["/random"])
    fun random(): ResponseEntity<*> = ResponseEntity.ok(randomService.getRandom())

    @Operation(summary = "This request will be ignored for tracing")
    @GetMapping(path = ["/ignore_trace"])
    fun willBeIgnored(): ResponseEntity<Map<String, String>> = ResponseEntity.ok(mapOf("result" to "ok"))

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
