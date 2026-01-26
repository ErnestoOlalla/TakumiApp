package com.efor18.takumi.di

import androidx.compose.runtime.Composable
import com.efor18.takumi.characterlist.di.characterListModule
import com.efor18.takumi.characterdetails.di.characterDetailsModule
import com.efor18.takumi.episodedetails.di.episodeDetailsModule
import com.efor18.takumi.common.navigation.Navigator
import com.efor18.takumi.domain.di.domainModule
import com.efor18.takumi.platformModule
import com.efor18.takumi.navigation.NavigatorHolder
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module

val sharedModule = module {
    includes(domainModule)

    // Provide Navigator instance through NavigatorHolder
    factory<Navigator> { 
        NavigatorHolder.navigator ?: error("Navigator not initialized")
    }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun WithKoin(
    content: @Composable () -> Unit
) {
    KoinMultiplatformApplication(
        config = KoinConfiguration(config = {
            modules(sharedModule, characterListModule, characterDetailsModule, episodeDetailsModule, platformModule())
        })
    ) {
        content()
    }
}