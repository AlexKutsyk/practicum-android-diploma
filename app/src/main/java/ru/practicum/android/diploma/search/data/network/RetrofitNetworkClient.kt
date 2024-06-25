package ru.practicum.android.diploma.search.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filter.data.dto.IndustriesResponse
import ru.practicum.android.diploma.filter.data.dto.IndustryDto
import ru.practicum.android.diploma.filter.data.dto.IndustryRequestDto
import ru.practicum.android.diploma.filter.data.dto.RegionDto
import ru.practicum.android.diploma.filter.data.dto.RegionsRequestDto
import ru.practicum.android.diploma.filter.data.dto.RegionsResponse
import ru.practicum.android.diploma.search.data.CONNECTION_ERROR
import ru.practicum.android.diploma.search.data.INCORRECT_REQUEST
import ru.practicum.android.diploma.search.data.SERVER_ERROR
import ru.practicum.android.diploma.search.data.api.HHApiService
import ru.practicum.android.diploma.search.data.api.NetworkClient
import ru.practicum.android.diploma.search.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.search.data.dto.reponse.Response
import ru.practicum.android.diploma.search.data.dto.reponse.VacanciesResponse
import ru.practicum.android.diploma.util.isConnected
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsRequestDto
import ru.practicum.android.diploma.vacancy.data.dto.response.VacancyDetailsResponse
import java.io.IOException

class RetrofitNetworkClient(
    private val apiService: HHApiService,
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            Response().apply { resultCode = CONNECTION_ERROR }
        }

        return getResponse(dto)
    }

    private suspend fun getResponse(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacancySearchRequest -> {
                        val response = apiService.searchVacancies(dto.page, options = dto.options)
                        val result = if (response.isSuccessful) {
                            val vacanciesResponse = response.body() as VacanciesResponse
                            vacanciesResponse.apply { resultCode = response.code() }
                        } else {
                            errorResponse(response)
                        }
                        result
                    }

                    is VacancyDetailsRequestDto -> {
                        val response = apiService.getVacancyDetails(dto.id, options = dto.options)
                        val result = if (response.isSuccessful) {
                            val detailResponse = response.body() as VacancyDetailsResponse
                            detailResponse.apply { resultCode = response.code() }
                        } else {
                            errorResponse(response)
                        }
                        result
                    }

                    is RegionsRequestDto -> {
                        val response = apiService.getRegions(options = dto.options)
                        val regionsResponse = if (response.isSuccessful) {
                            val list = response.body() as List<RegionDto>
                            RegionsResponse(list).apply { resultCode = response.code() }
                        } else {
                            errorResponse(response)
                        }
                        regionsResponse
                    }

                    is IndustryRequestDto -> {
                        val response = apiService.getIndustries(options = dto.options)
                        val industriesResponse = if (response.isSuccessful) {
                            val industries = response.body() as List<IndustryDto>
                            IndustriesResponse(industries).apply { resultCode = response.code() }
                        } else {
                            errorResponse(response)
                        }
                        industriesResponse
                    }

                    else -> {
                        Response().apply { resultCode = INCORRECT_REQUEST }
                    }
                }
            } catch (e: IOException) {
                Log.e("RetrofitNetworkClient", "exception handled $e")
                Response().apply { resultCode = SERVER_ERROR }
            }
        }
    }

    private fun <T> errorResponse(response: retrofit2.Response<T>): Response {
        val code = response.code()
        return Response().apply { resultCode = code }
    }
}
