package com.example.userservice.config

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.trace.export.SpanExporter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ObservabilityConfig {
    @Value("\${otel.exporter.otlp.traces.endpoint:http://localhost:4317}")
    private lateinit var otlpEndpoint: String

    @Bean
    fun spanExporter(): SpanExporter = OtlpGrpcSpanExporter.builder().setEndpoint(otlpEndpoint).build()

    @Bean
    fun observedAspect(observationRegistry: ObservationRegistry): ObservedAspect = ObservedAspect(observationRegistry)

}