package com.efor18.takumi.domain.di

import com.efor18.takumi.data.di.dataModule
import com.efor18.takumi.domain.usecase.GetCharacterDetailsUseCase
import com.efor18.takumi.domain.usecase.GetCharactersUseCase
import com.efor18.takumi.domain.usecase.GetEpisodeDetailsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val domainModule = module {
    includes(dataModule)
    factoryOf(::GetCharacterDetailsUseCase)
    factoryOf(::GetCharactersUseCase)
    factoryOf(::GetEpisodeDetailsUseCase)
}
