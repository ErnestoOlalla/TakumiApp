plugins {
    alias(libs.plugins.takumi.featureModule)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Feature-specific dependencies
            implementation(libs.kermit)
        }
    }
}
