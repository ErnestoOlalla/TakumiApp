plugins {
    alias(libs.plugins.takumi.kotlinMultiplatform)
    alias(libs.plugins.apollo)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(projects.shared.common)
            implementation(libs.apollo.runtime.kotlin)
        }
        iosMain.dependencies {
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
        }
        
        androidUnitTest.dependencies {
            implementation(libs.kotlin.testJunit)
            implementation(libs.mockk)
        }
    }
}

apollo {
    service("rickandmorty") {
        packageName.set("com.efor18.takumi.data.remote.rickandmorty")
    }
}