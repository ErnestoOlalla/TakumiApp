package com.efor18.takumi.episodedetails.di

import com.efor18.takumi.episodedetails.viewmodel.EpisodeDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val episodeDetailsModule = module {
    viewModel { 
        EpisodeDetailsViewModel(
            getEpisodeDetailsUseCase = get(),
            navigator = get()
        )
    }
}
