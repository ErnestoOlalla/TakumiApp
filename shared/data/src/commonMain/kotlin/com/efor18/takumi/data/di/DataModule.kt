package com.efor18.takumi.data.di

import com.efor18.takumi.data.repository.APIRepository
import com.efor18.takumi.data.repository.APIRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<APIRepository> {
        APIRepositoryImpl()
    }
}