package com.example.android4a.injection

import com.example.android4a.data.repository.UserRepository
import com.example.android4a.domain.usecase.CreateUserUseCase
import com.example.android4a.presentation.main.MainViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {
    factory { MainViewModel(get()) }
}



val domainModule = module {
    factory { CreateUserUseCase(get()) }
}


val dataModule = module {
    single { UserRepository() }
}