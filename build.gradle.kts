import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    application
    `maven-publish`
}

group = "com.cool.jerry"
version = "1.0.0"

repositories {
    maven(url = "https://maven.aliyun.com/repository/public")
//    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2") // 请使用最新版本


    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}


publishing{
    publications{
        create("maven_public",MavenPublication::class){
            groupId = "com.cool.jerry"
            artifactId = "RuleEngine"
            version = version
            from(components.getByName("java"))
        }
    }
}



