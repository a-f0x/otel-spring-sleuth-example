import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

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
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnit()
    }

    extra["springCloudVersion"] = "2021.0.3"
    extra["springSleuthOtelVersion"] = "1.1.0-M6"


    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        runtimeOnly("io.micrometer:micrometer-registry-prometheus")
        implementation("net.logstash.logback:logstash-logback-encoder:7.2")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.cloud:spring-cloud-stream")
        implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
        implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.9")
        implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")
        implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
        implementation("org.apache.kafka:kafka-clients:2.7.1")
        implementation("org.springframework.cloud:spring-cloud-starter-sleuth") {
            exclude("org.springframework.cloud", "spring-cloud-sleuth-brave")
        }
        implementation("org.springframework.cloud:spring-cloud-sleuth-otel-autoconfigure")
        implementation("io.opentelemetry:opentelemetry-exporter-otlp")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
            mavenBom("org.springframework.cloud:spring-cloud-sleuth-otel-dependencies:${property("springSleuthOtelVersion")}")
        }
    }
}