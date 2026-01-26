package com.efor18.takumi.characterdetails.di

import com.efor18.takumi.characterdetails.viewmodel.CharacterDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val characterDetailsModule = module {
    viewModel { 
        CharacterDetailsViewModel(
            getCharacterDetailsUseCase = get(),
            navigator = get()
        )
    }
}
