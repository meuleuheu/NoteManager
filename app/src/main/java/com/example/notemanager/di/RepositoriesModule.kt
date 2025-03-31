package com.example.notemanager.di

import com.example.notemanager.repositories.Api
import com.example.notemanager.repositories.ApiImpl
import com.example.notemanager.repositories.MainLog
import com.example.notemanager.repositories.MainLogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindMainLog(log: MainLogImpl) : MainLog

    @Binds
    @Singleton
    abstract fun bindApi(api: ApiImpl) : Api
}