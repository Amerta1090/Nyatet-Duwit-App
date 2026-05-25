package com.nyatetduwit.presentation.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Goal
import com.nyatetduwit.domain.usecase.goal.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class GoalUiState(
    val goals: List<Goal> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val formState: GoalFormState = GoalFormState(),
)

data class GoalFormState(
    val id: String = "",
    val name: String = "",
    val targetAmount: String = "",
    val currentAmount: String = "0",
    val deadline: Long? = null,
    val icon: String = "flag",
    val color: String = "#1A5C53",
)

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val getActiveGoalsUseCase: GetActiveGoalsUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
    private val addGoalUseCase: AddGoalUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val addGoalProgressUseCase: AddGoalProgressUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalUiState())
    val uiState: StateFlow<GoalUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getActiveGoalsUseCase().collect { goals ->
                _uiState.update { it.copy(goals = goals, isLoading = false) }
            }
        }
    }

    fun loadGoal(goalId: String?) {
        if (goalId == null || goalId == "null") {
            _uiState.update { it.copy(formState = GoalFormState(), isLoading = false) }
            return
        }
        viewModelScope.launch {
            val goal = getGoalByIdUseCase(goalId)
            if (goal != null) {
                _uiState.update {
                    it.copy(
                        formState = GoalFormState(
                            id = goal.id,
                            name = goal.name,
                            targetAmount = goal.targetAmount.toString(),
                            currentAmount = goal.currentAmount.toString(),
                            deadline = goal.deadline,
                            icon = goal.icon,
                            color = goal.color,
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) { _uiState.update { it.copy(formState = it.formState.copy(name = name)) } }
    fun onTargetAmountChange(amount: String) { _uiState.update { it.copy(formState = it.formState.copy(targetAmount = amount)) } }
    fun onCurrentAmountChange(amount: String) { _uiState.update { it.copy(formState = it.formState.copy(currentAmount = amount)) } }
    fun onIconChange(icon: String) { _uiState.update { it.copy(formState = it.formState.copy(icon = icon)) } }
    fun onColorChange(color: String) { _uiState.update { it.copy(formState = it.formState.copy(color = color)) } }

    fun saveGoal(onSuccess: () -> Unit = {}) {
        val form = _uiState.value.formState
        if (form.name.isBlank()) { _uiState.update { it.copy(error = "Nama tujuan tidak boleh kosong") }; return }
        val targetAmount = form.targetAmount.toLongOrNull() ?: run {
            _uiState.update { it.copy(error = "Nominal target tidak valid") }; return
        }

        viewModelScope.launch {
            try {
                val goal = Goal(
                    id = if (form.id.isEmpty()) UUID.randomUUID().toString() else form.id,
                    name = form.name.trim(),
                    targetAmount = targetAmount,
                    currentAmount = form.currentAmount.toLongOrNull() ?: 0,
                    deadline = form.deadline,
                    icon = form.icon,
                    color = form.color,
                )
                if (form.id.isEmpty()) addGoalUseCase(goal) else updateGoalUseCase(goal)
                _uiState.update { it.copy(isSuccess = true, formState = GoalFormState()) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch { deleteGoalUseCase(goal) }
    }

    fun addProgress(id: String, amount: Long) {
        viewModelScope.launch {
            try { addGoalProgressUseCase(id, amount) }
            catch (e: Exception) { _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
    fun clearSuccess() { _uiState.update { it.copy(isSuccess = false) } }
}
