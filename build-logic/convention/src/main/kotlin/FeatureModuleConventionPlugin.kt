import com.efor18.takumi.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeatureModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.efor18.takumi.kotlinMultiplatform")
            apply("com.efor18.takumi.composeMultiplatform")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain {
                    dependencies {
                        // Core feature module dependencies
                        implementation(project(":shared:common"))
                        implementation(project(":shared:domain"))
                        implementation(project(":shared:presentation:designsystem"))
                        
                        // ViewModel dependencies
                        implementation(libs.findLibrary("androidx-lifecycle-viewmodelCompose").get())
                        implementation(libs.findLibrary("koin-core").get())
                    }
                }
                commonTest {
                    dependencies {
                        implementation(libs.findLibrary("kotlin-test").get())
                    }
                }
            }
        }
    }
}