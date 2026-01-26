plugins {
    `kotlin-dsl`
}

group = "com.efor18.takumi.buildlogic"

dependencies {
    compileOnly(libs.plugins.kotlinx.serialization.toDep())
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.androidLibrary.toDep())
    compileOnly(libs.plugins.composeMultiplatform.toDep())
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
    compileOnly(libs.plugins.composeCompiler.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform"){
            id = "com.efor18.takumi.kotlinMultiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }
        register("composeMultiplatform"){
            id = "com.efor18.takumi.composeMultiplatform"
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }
        register("featureModule"){
            id = "com.efor18.takumi.featureModule"
            implementationClass = "FeatureModuleConventionPlugin"
        }
    }
}
