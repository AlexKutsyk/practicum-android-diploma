package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.db.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.favorites.domain.api.FavoriteRepository
import ru.practicum.android.diploma.favorites.domain.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.filter.data.impl.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.data.impl.SettingsRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.api.SettingsInteractor
import ru.practicum.android.diploma.filter.domain.api.SettingsRepository
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.SettingsInteractorImpl
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.share.domain.api.SharingInteractor
import ru.practicum.android.diploma.share.domain.impl.SharingInteractorImpl
import ru.practicum.android.diploma.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyDetailsInteractorImpl

val domainModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
            converter = get()
        )
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(
            networkClient = get(),
            converter = get()
        )
    }
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            appDatabase = get(),
            favoriteConverter = get()
        )
    }

    single<SharingInteractor> {
        SharingInteractorImpl(
            externalNavigator = get()
        )
    }

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(
            vacancyDetailsRepository = get()
        )
    }

    single<SearchInteractor> {
        SearchInteractorImpl(
            searchRepository = get()
        )
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(favoriteRepository = get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get(), get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get())
    }
}
