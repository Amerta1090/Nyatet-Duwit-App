package com.nyatetduwit.presentation.investment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Investment
import com.nyatetduwit.domain.model.InvestmentType
import com.nyatetduwit.domain.usecase.investment.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class InvestmentUiState(
    val investments: List<Investment> = emptyList(),
    val totalValue: Long = 0L,
    val totalCostBasis: Long = 0L,
    val formState: InvestmentFormState = InvestmentFormState(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
)

data class InvestmentFormState(
    val id: String = "",
    val name: String = "",
    val type: InvestmentType = InvestmentType.STOCK,
    val currentValue: Long = 0L,
    val costBasis: Long = 0L,
    val currencyCode: String = "IDR",
    val accountId: String? = null,
    val notes: String? = null,
)

@HiltViewModel
class InvestmentViewModel @Inject constructor(
    getActiveInvestmentsUseCase: GetActiveInvestmentsUseCase,
    getTotalInvestmentValueUseCase: GetTotalInvestmentValueUseCase,
    getTotalCostBasisUseCase: GetTotalCostBasisUseCase,
    private val getInvestmentByIdUseCase: GetInvestmentByIdUseCase,
    private val addInvestmentUseCase: AddInvestmentUseCase,
    private val updateInvestmentUseCase: UpdateInvestmentUseCase,
    private val deleteInvestmentUseCase: DeleteInvestmentUseCase,
    private val deactivateInvestmentUseCase: DeactivateInvestmentUseCase,
    private val updateInvestmentValueUseCase: UpdateInvestmentValueUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvestmentUiState())
    val uiState: StateFlow<InvestmentUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getActiveInvestmentsUseCase(),
                getTotalInvestmentValueUseCase(),
                getTotalCostBasisUseCase(),
            ) { investments, totalValue, totalCostBasis ->
                InvestmentUiState(
                    investments = investments,
                    totalValue = totalValue,
                    totalCostBasis = totalCostBasis,
                    isLoading = false,
                )
            }.collect { state ->
                _uiState.update { state.copy(formState = _uiState.value.formState) }
            }
        }
    }

    fun loadInvestment(investmentId: String?) {
        if (investmentId == null) {
            _uiState.update { it.copy(formState = InvestmentFormState(), isLoading = false) }
            return
        }
        viewModelScope.launch {
            val investment = getInvestmentByIdUseCase(investmentId)
            if (investment != null) {
                _uiState.update {
                    it.copy(
                        formState = InvestmentFormState(
                            id = investment.id,
                            name = investment.name,
                            type = investment.type,
                            currentValue = investment.currentValue,
                            costBasis = investment.costBasis,
                            currencyCode = investment.currencyCode,
                            accountId = investment.accountId,
                            notes = investment.notes,
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) { _uiState.update { it.copy(formState = it.formState.copy(name = name)) } }
    fun onTypeChange(type: InvestmentType) { _uiState.update { it.copy(formState = it.formState.copy(type = type)) } }
    fun onCurrentValueChange(value: Long) { _uiState.update { it.copy(formState = it.formState.copy(currentValue = value)) } }
    fun onCostBasisChange(value: Long) { _uiState.update { it.copy(formState = it.formState.copy(costBasis = value)) } }
    fun onCurrencyChange(currency: String) { _uiState.update { it.copy(formState = it.formState.copy(currencyCode = currency)) } }
    fun onNotesChange(notes: String) { _uiState.update { it.copy(formState = it.formState.copy(notes = notes)) } }

    fun saveInvestment() {
        val form = _uiState.value.formState
        if (form.name.isBlank()) {
            _uiState.update { it.copy(error = "Nama investasi tidak boleh kosong") }
            return
        }
        viewModelScope.launch {
            val investment = Investment(
                id = if (form.id.isEmpty()) UUID.randomUUID().toString() else form.id,
                name = form.name.trim(),
                type = form.type,
                currentValue = form.currentValue,
                costBasis = form.costBasis,
                currencyCode = form.currencyCode,
                accountId = form.accountId,
                notes = form.notes,
                createdAt = if (form.id.isEmpty()) System.currentTimeMillis() else 0L,
                updatedAt = System.currentTimeMillis(),
            )
            if (form.id.isEmpty()) addInvestmentUseCase(investment)
            else updateInvestmentUseCase(investment)
            _uiState.update { it.copy(formState = InvestmentFormState(), isSuccess = true) }
        }
    }

    fun deleteInvestment(investment: Investment) {
        viewModelScope.launch {
            deleteInvestmentUseCase(investment)
            _uiState.update { it.copy(isSuccess = true) }
        }
    }

    fun updateValue(investmentId: String, value: Long) {
        viewModelScope.launch {
            updateInvestmentValueUseCase(investmentId, value)
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
    fun clearSuccess() { _uiState.update { it.copy(isSuccess = false) } }
}
