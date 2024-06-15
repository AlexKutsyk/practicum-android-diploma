package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.db.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.VacancyRepository
import ru.practicum.android.diploma.favorites.domain.api.VacansyInteractor
import ru.practicum.android.diploma.favorites.domain.impl.VacancyInteractorImpl
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

val domainModule = module{

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(
            vacancyDetailsRepository = get()
        )
    }

    single<SharingInteractor> {
        SharingInteractorImpl(
            externalNavigator = get()
        )
    }

    single<SearchInteractor> {
        SearchInteractorImpl(
            searchRepository = get()
        )
    }

    single<VacansyInteractor> {
        VacancyInteractorImpl(
            vacancyRepository = get()
        )
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(
            networkClient = get(),
            converter = get()
        )
    }

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
            converter = get()
        )
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            appDatabase = get(),
            vacancyConverter = get()
        )
    }
}
