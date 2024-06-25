package ru.practicum.android.diploma.search.data.impl

import ru.practicum.android.diploma.search.data.CONNECTION_ERROR
import ru.practicum.android.diploma.search.data.INCORRECT_REQUEST
import ru.practicum.android.diploma.search.data.SUCCESS
import ru.practicum.android.diploma.search.data.VacancyConverter
import ru.practicum.android.diploma.search.data.api.NetworkClient
import ru.practicum.android.diploma.search.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.search.data.dto.reponse.VacanciesResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Errors
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.search.domain.models.VacanciesSearchRequest

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: VacancyConverter
) : SearchRepository {
    override suspend fun searchVacancies(request: VacanciesSearchRequest): SearchResult {
        val options: HashMap<String, String> = createOptionalFields(request)
        val response = networkClient.doRequest(
            VacancySearchRequest(request.page, options)
        )

        return when (response.resultCode) {
            CONNECTION_ERROR -> {
                SearchResult.Error(Errors.ConnectionError)
            }

            SUCCESS -> {
                converter.map(response as VacanciesResponse)
            }

            INCORRECT_REQUEST -> {
                SearchResult.Error(Errors.IncorrectRequest)
            }

            else -> {
                SearchResult.Error(Errors.ServerError)
            }
        }
    }
    
    private fun createOptionalFields(request: VacanciesSearchRequest): HashMap<String, String> {
        val industryId: String = request.filterSettings.industry.id
        val onlyWithSalary: Boolean = request.filterSettings.onlyWithSalary
        val areaId: String = request.filterSettings.area.id
        val salary: Long = request.filterSettings.salary
        val options: HashMap<String, String> = HashMap()
        options["text"] = request.searchString
        options["only_with_salary"] = onlyWithSalary.toString()
        if (industryId.isNotEmpty()) {
            options["industry"] = industryId
        }
        if (areaId.isNotEmpty()) {
            options["area"] = areaId
        }
        if (salary > 0) {
            options["salary"] = salary.toString()
        }
        return options
    }
}
