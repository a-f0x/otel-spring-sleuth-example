plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}
tasks.bootJar {
    enabled = true
    archiveFileName.set("app.jar")
    mainClass.set("com.example.randomservice.RandomServiceApplicationKt")
}
