package com.priambudi19.myfileapp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.priambudi19.myfileapp.data.local.MyDao
import com.priambudi19.myfileapp.data.local.MyDatabase
import com.priambudi19.myfileapp.data.repo.MainRepository
import com.priambudi19.myfileapp.data.repo.MainRepositoryImpl
import com.priambudi19.myfileapp.ui.add.AddFileViewModel
import com.priambudi19.myfileapp.ui.detail.DetailViewModel
import com.priambudi19.myfileapp.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), MyDatabase::class.java, MyDatabase.DB_NAME)
            .fallbackToDestructiveMigration().build()
    }
    single {
        get<MyDatabase>().dao()
    }

    single<MainRepository> {
        MainRepositoryImpl(get())
    }
    viewModel {
        AddFileViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        DetailViewModel(get())
    }
}