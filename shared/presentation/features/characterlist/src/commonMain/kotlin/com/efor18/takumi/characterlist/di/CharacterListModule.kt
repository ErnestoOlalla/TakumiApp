package com.efor18.takumi.characterlist.di

import com.efor18.takumi.characterlist.viewmodel.CharacterListViewModel
import org.koin.dsl.module

val characterListModule = module {
    factory<CharacterListViewModel> {
        CharacterListViewModel(
            getCharactersUseCase = get(),
            navigator = get()
        )
    }
}
