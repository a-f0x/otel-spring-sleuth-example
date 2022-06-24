plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}
tasks.bootJar {
    enabled = true
    archiveFileName.set("app.jar")
    mainClass.set("com.example.userservice.UserServiceApplication")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    runtimeOnly("com.h2database:h2")
}