plugins {
    alias(libs.plugins.takumi.kotlinMultiplatform)
    alias(libs.plugins.takumi.composeMultiplatform)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.efor18.takumi.res"
    generateResClass = always
}