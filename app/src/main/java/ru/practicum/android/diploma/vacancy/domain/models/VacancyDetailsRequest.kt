package ru.practicum.android.diploma.vacancy.domain.models

data class VacancyDetailsRequest(
    val id: String,
    val locale: String = "RU",
    val host: String = "hh.ru"
)
