package com.example.android4a.injection

import android.content.Context
import androidx.room.Room
import com.example.android4a.domain.usecase.CreateUserUseCase
import com.example.android4a.domain.usecase.GetUserUseCase
import com.example.android4a.local.AppDatabase
import com.example.android4a.local.DatabaseDaoDao
import com.example.android4a.presentation.logged.LoggedViewModel
import com.example.android4a.presentation.main.MainViewModel
import com.example.android4a.presentation.register.RegisterViewModel
import com.example.android4a.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Init the [ViewModel]
 */
val presentationModule = module {
    factory { MainViewModel(get()) }
    factory { RegisterViewModel(get(), get()) }
    factory { LoggedViewModel() }
}

/**
 * Init the useCases
 */
val domainModule = module {
    factory { CreateUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
}

/**
 * Init the repositories
 */
val dataModule = module {
    single { UserRepository(get()) }
    single { createDataBase(androidContext()) }
}

/**
 * Init the database
 */
fun createDataBase(context: Context): DatabaseDaoDao {
    val appDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()
    return appDatabase.dataBaseDao()
}
