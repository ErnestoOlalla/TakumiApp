plugins {
    alias(libs.plugins.takumi.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)
        }
        
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.serialization.json)
        }
        
        androidUnitTest.dependencies {
            implementation(libs.kotlin.testJunit)
        }
    }
}
