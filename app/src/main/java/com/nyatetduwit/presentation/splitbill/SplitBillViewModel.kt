package com.nyatetduwit.presentation.splitbill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.SplitBill
import com.nyatetduwit.domain.model.SplitBillPerson
import com.nyatetduwit.domain.usecase.splitbill.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class SplitBillUiState(
    val bills: List<SplitBill> = emptyList(),
    val formState: SplitBillFormState = SplitBillFormState(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
)

data class SplitBillFormState(
    val id: String = "",
    val title: String = "",
    val totalAmount: Long = 0L,
    val paidBy: String = "",
    val notes: String? = null,
    val persons: List<SplitBillPersonInput> = listOf(SplitBillPersonInput()),
)

data class SplitBillPersonInput(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val amount: Long = 0L,
)

@HiltViewModel
class SplitBillViewModel @Inject constructor(
    getAllBillsUseCase: GetAllBillsUseCase,
    private val getBillByIdUseCase: GetBillByIdUseCase,
    private val addBillUseCase: AddBillUseCase,
    private val updateBillUseCase: UpdateBillUseCase,
    private val deleteBillUseCase: DeleteBillUseCase,
    private val markSettledUseCase: MarkSettledUseCase,
    private val markPersonPaidUseCase: MarkPersonPaidUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplitBillUiState())
    val uiState: StateFlow<SplitBillUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllBillsUseCase().collect { bills ->
                _uiState.update { it.copy(bills = bills, isLoading = false) }
            }
        }
    }

    fun loadBill(billId: String?) {
        if (billId == null) {
            _uiState.update { it.copy(formState = SplitBillFormState(), isLoading = false) }
            return
        }
        viewModelScope.launch {
            val bill = getBillByIdUseCase(billId)
            if (bill != null) {
                _uiState.update {
                    it.copy(
                        formState = SplitBillFormState(
                            id = bill.id,
                            title = bill.title,
                            totalAmount = bill.totalAmount,
                            paidBy = bill.paidBy,
                            notes = bill.notes,
                            persons = bill.persons.map { p ->
                                SplitBillPersonInput(id = p.id, name = p.name, amount = p.amount)
                            },
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onTitleChange(title: String) { _uiState.update { it.copy(formState = it.formState.copy(title = title)) } }
    fun onTotalAmountChange(amount: Long) { _uiState.update { it.copy(formState = it.formState.copy(totalAmount = amount)) } }
    fun onPaidByChange(paidBy: String) { _uiState.update { it.copy(formState = it.formState.copy(paidBy = paidBy)) } }
    fun onNotesChange(notes: String) { _uiState.update { it.copy(formState = it.formState.copy(notes = notes)) } }

    fun onPersonNameChange(index: Int, name: String) {
        val persons = _uiState.value.formState.persons.toMutableList()
        if (index < persons.size) persons[index] = persons[index].copy(name = name)
        _uiState.update { it.copy(formState = it.formState.copy(persons = persons)) }
    }

    fun onPersonAmountChange(index: Int, amount: Long) {
        val persons = _uiState.value.formState.persons.toMutableList()
        if (index < persons.size) persons[index] = persons[index].copy(amount = amount)
        _uiState.update { it.copy(formState = it.formState.copy(persons = persons)) }
    }

    fun addPerson() {
        val persons = _uiState.value.formState.persons + SplitBillPersonInput()
        _uiState.update { it.copy(formState = it.formState.copy(persons = persons)) }
    }

    fun removePerson(index: Int) {
        val persons = _uiState.value.formState.persons.toMutableList()
        if (persons.size > 1 && index < persons.size) persons.removeAt(index)
        _uiState.update { it.copy(formState = it.formState.copy(persons = persons)) }
    }

    fun saveBill() {
        val form = _uiState.value.formState
        if (form.title.isBlank()) {
            _uiState.update { it.copy(error = "Judul tidak boleh kosong") }
            return
        }
        viewModelScope.launch {
            val bill = SplitBill(
                id = if (form.id.isEmpty()) UUID.randomUUID().toString() else form.id,
                title = form.title.trim(),
                totalAmount = form.totalAmount,
                paidBy = form.paidBy.trim(),
                notes = form.notes,
                persons = form.persons.filter { it.name.isNotBlank() }.map { p ->
                    SplitBillPerson(
                        id = p.id,
                        splitBillId = if (form.id.isEmpty()) "" else form.id,
                        name = p.name,
                        amount = p.amount,
                    )
                },
                createdAt = if (form.id.isEmpty()) System.currentTimeMillis() else 0L,
                updatedAt = System.currentTimeMillis(),
            )
            if (form.id.isEmpty()) addBillUseCase(bill)
            else updateBillUseCase(bill)
            _uiState.update { it.copy(formState = SplitBillFormState(), isSuccess = true) }
        }
    }

    fun deleteBill(bill: SplitBill) {
        viewModelScope.launch { deleteBillUseCase(bill); _uiState.update { it.copy(isSuccess = true) } }
    }

    fun markSettled(id: String) {
        viewModelScope.launch { markSettledUseCase(id) }
    }

    fun markPersonPaid(personId: String) {
        viewModelScope.launch { markPersonPaidUseCase(personId) }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
    fun clearSuccess() { _uiState.update { it.copy(isSuccess = false) } }
}
