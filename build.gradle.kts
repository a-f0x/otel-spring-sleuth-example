import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
}
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

allprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("java")
    }
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/plugins-snapshot")
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnit()
    }

    extra["springCloudVersion"] = "2022.0.2"


    dependencies {

        //spring
        implementation("net.logstash.logback:logstash-logback-encoder:7.3")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


        //observability
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        runtimeOnly("io.micrometer:micrometer-registry-prometheus")
        implementation("io.micrometer:micrometer-tracing")
        implementation("io.micrometer:micrometer-tracing-bridge-otel")
        implementation("io.opentelemetry:opentelemetry-exporter-otlp")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("io.github.openfeign:feign-micrometer")


        //cloud
        implementation("org.springframework.cloud:spring-cloud-stream")

        implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
        implementation("org.apache.kafka:kafka-clients:3.1.0")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }
}