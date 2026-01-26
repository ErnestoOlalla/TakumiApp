import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.takumi.kotlinMultiplatform)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.common)
            implementation(projects.shared.data)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
