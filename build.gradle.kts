import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.9.20"
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral() // Thêm kho lưu trữ Maven Central
    google()
}
val sqlDelightVersion = "1.5.3"
val voyagerVersion = "1.0.0"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("io.ktor:ktor-client-cio-jvm:2.3.9")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")

    implementation("media.kamel:kamel-image:0.9.4")

    implementation ("org.slf4j:slf4j-api:1.7.32")
    implementation ("ch.qos.logback:logback-classic:1.2.6")

    implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
    implementation("com.squareup.sqldelight:coroutines-extensions:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")

    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    // Kodein integration
    implementation("cafe.adriel.voyager:voyager-kodein:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")

//    material-theme-builder

    implementation("org.jetbrains.compose.material3:material3-desktop:1.2.1")
    implementation(compose.desktop.currentOs)
}



compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WeatherApp"
            packageVersion = "1.0.0"
        }
    }
}


sqldelight {
    database("search_history") { // This will be the name of the generated database class.
        packageName = "com.db.search_history"
    }
}