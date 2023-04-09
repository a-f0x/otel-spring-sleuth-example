plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}
tasks.bootJar {
    enabled = true
    archiveFileName.set("app.jar")
    mainClass.set("com.example.userservice.UserServiceApplicationKt")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("com.h2database:h2")
}