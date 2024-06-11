package ru.practicum.android.diploma.search.ui.models

import ru.practicum.android.diploma.search.domain.models.Errors
import ru.practicum.android.diploma.search.domain.models.VacancyPreview

sealed interface SearchUiState {
    data object Default : SearchUiState
    data object EditingRequest : SearchUiState
    data object Loading : SearchUiState
    data class SearchResult(val vacancies: List<VacancyPreview>) : SearchUiState
    data object EmptyResult : SearchUiState
    data class Error(val error: Errors) : SearchUiState
}
