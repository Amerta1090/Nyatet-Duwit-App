package com.nyatetduwit.presentation.debt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Debt
import com.nyatetduwit.domain.model.DebtPayment
import com.nyatetduwit.domain.model.DebtType
import com.nyatetduwit.domain.usecase.debt.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class DebtUiState(
    val debts: List<Debt> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val formState: DebtFormState = DebtFormState(),
    val selectedDebt: Debt? = null,
    val payments: List<DebtPayment> = emptyList(),
)

data class DebtFormState(
    val id: String = "",
    val type: DebtType = DebtType.OWE,
    val personName: String = "",
    val amount: String = "",
    val remainingAmount: String = "",
    val dueDate: Long? = null,
    val notes: String = "",
)

@HiltViewModel
class DebtViewModel @Inject constructor(
    private val getActiveDebtsUseCase: GetActiveDebtsUseCase,
    private val getDebtByIdUseCase: GetDebtByIdUseCase,
    private val addDebtUseCase: AddDebtUseCase,
    private val updateDebtUseCase: UpdateDebtUseCase,
    private val deleteDebtUseCase: DeleteDebtUseCase,
    private val getDebtPaymentsUseCase: GetDebtPaymentsUseCase,
    private val addDebtPaymentUseCase: AddDebtPaymentUseCase,
    private val deleteDebtPaymentUseCase: DeleteDebtPaymentUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DebtUiState())
    val uiState: StateFlow<DebtUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getActiveDebtsUseCase().collect { debts ->
                _uiState.update { it.copy(debts = debts, isLoading = false) }
            }
        }
    }

    fun loadDebt(debtId: String?) {
        if (debtId == null || debtId == "null") {
            _uiState.update { it.copy(formState = DebtFormState(), isLoading = false) }
            return
        }
        viewModelScope.launch {
            val debt = getDebtByIdUseCase(debtId)
            if (debt != null) {
                _uiState.update {
                    it.copy(
                        formState = DebtFormState(
                            id = debt.id,
                            type = debt.type,
                            personName = debt.personName,
                            amount = debt.amount.toString(),
                            remainingAmount = debt.remainingAmount.toString(),
                            dueDate = debt.dueDate,
                            notes = debt.notes ?: "",
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun loadDebtDetail(debtId: String) {
        viewModelScope.launch {
            val debt = getDebtByIdUseCase(debtId)
            _uiState.update { it.copy(selectedDebt = debt) }
        }
        viewModelScope.launch {
            getDebtPaymentsUseCase(debtId).collect { payments ->
                _uiState.update { it.copy(payments = payments) }
            }
        }
    }

    fun onTypeChange(type: DebtType) { _uiState.update { it.copy(formState = it.formState.copy(type = type)) } }
    fun onPersonNameChange(name: String) { _uiState.update { it.copy(formState = it.formState.copy(personName = name)) } }
    fun onAmountChange(amount: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(
                amount = amount,
                remainingAmount = amount
            ))
        }
    }
    fun onNotesChange(notes: String) { _uiState.update { it.copy(formState = it.formState.copy(notes = notes)) } }

    fun saveDebt(onSuccess: () -> Unit = {}) {
        val form = _uiState.value.formState
        if (form.personName.isBlank()) { _uiState.update { it.copy(error = "Nama tidak boleh kosong") }; return }
        val amount = form.amount.toLongOrNull() ?: run { _uiState.update { it.copy(error = "Nominal tidak valid") }; return }

        viewModelScope.launch {
            try {
                val debt = Debt(
                    id = if (form.id.isEmpty()) UUID.randomUUID().toString() else form.id,
                    type = form.type,
                    personName = form.personName.trim(),
                    amount = amount,
                    remainingAmount = amount,
                    dueDate = form.dueDate,
                    notes = form.notes.ifBlank { null },
                )
                if (form.id.isEmpty()) addDebtUseCase(debt) else updateDebtUseCase(debt)
                _uiState.update { it.copy(isSuccess = true, formState = DebtFormState()) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteDebt(debt: Debt) {
        viewModelScope.launch { deleteDebtUseCase(debt) }
    }

    fun addPayment(debtId: String, amount: Long) {
        viewModelScope.launch {
            try {
                addDebtPaymentUseCase(DebtPayment(
                    id = UUID.randomUUID().toString(),
                    debtId = debtId,
                    amount = amount,
                ))
                loadDebtDetail(debtId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deletePayment(payment: DebtPayment, debtId: String) {
        viewModelScope.launch {
            deleteDebtPaymentUseCase(payment)
            loadDebtDetail(debtId)
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
    fun clearSuccess() { _uiState.update { it.copy(isSuccess = false) } }
}
