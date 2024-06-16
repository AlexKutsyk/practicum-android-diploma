package ru.practicum.android.diploma.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.search.domain.models.Errors
import ru.practicum.android.diploma.share.domain.api.SharingInteractor
import ru.practicum.android.diploma.share.domain.models.EmailData
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsRequest
import ru.practicum.android.diploma.vacancy.ui.models.VacancyDetailsUIState

open class VacancyDetailsViewModel(
    id: String,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val sharingInteractor: SharingInteractor,
    private val favoriteInteractor: FavoriteInteractor,
) : ViewModel() {

    private val idVacancy = id

    val vacancyDetailsState: MutableLiveData<VacancyDetailsUIState> = MutableLiveData()
    fun getUIState(): LiveData<VacancyDetailsUIState> = vacancyDetailsState

    init {
        getVacancyDetails(id)
    }

    private fun getVacancyDetails(id: String) {
        vacancyDetailsState.value = VacancyDetailsUIState.Loading
        viewModelScope.launch {
            val result = vacancyDetailsInteractor.getVacancyDetails(
                VacancyDetailsRequest(id = id)
            )
            processResult(result.first, result.second)
        }
    }

    fun shareVacancy(link: String) {
        sharingInteractor.shareVacancy(link)
    }

    fun sendEmail(emailData: EmailData) {
        sharingInteractor.sendEmail(emailData)
    }

    fun callTo(number: String) {
        sharingInteractor.callTo(number)
    }

    fun processResult(details: VacancyDetails?, errors: Errors?) {
        if (details != null) {
            vacancyDetailsState.value = VacancyDetailsUIState.Content(details)
        } else {
            vacancyDetailsState.value = VacancyDetailsUIState.Error(errors)
        }
    }

    fun changeFavoriteState(vacancyDetails: VacancyDetails) {
        viewModelScope.launch {
            vacancyDetails.isFavorite != vacancyDetails.isFavorite
            if (vacancyDetails.isFavorite) {
                favoriteInteractor.deleteFavoriteVacancyById(vacancyDetails.id)
            } else {
                favoriteInteractor.insertFavoriteVacancy(vacancyDetails)
            }
            getVacancyDetails(idVacancy)
        }
    }
}
