package com.br.smobile.com.result.di

import android.app.Application
import com.br.smobile.com.result.database.AppDataBase
import com.br.smobile.com.result.database.ResultDAO
import com.br.smobile.com.result.repository.ResultRepository
import com.br.smobile.com.result.repository.ResultRepositoryImp
import com.br.smobile.com.result.viewmodel.ResultViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appsModule = module {
    fun provideDataBase(application: Application): AppDataBase {
        return AppDataBase.buildDatabase(application)
    }

    fun provideDAO(database: AppDataBase): ResultDAO {
        return database.resultAPI()
    }

    fun provideRepository(dao: ResultDAO): ResultRepository {
        return ResultRepositoryImp(dao)
    }

    single { provideRepository(get()) }
    single { provideDataBase(androidApplication()) }
    single { provideDAO(get()) }
    viewModel { ResultViewModel(get()) }
}
