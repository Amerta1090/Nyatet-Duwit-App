package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Investment
import kotlinx.coroutines.flow.Flow

interface InvestmentRepository {
    fun getAllActiveInvestments(): Flow<List<Investment>>
    fun getAllInvestments(): Flow<List<Investment>>
    suspend fun getInvestmentById(id: String): Investment?
    fun getTotalInvestmentValue(): Flow<Long>
    fun getTotalCostBasis(): Flow<Long>
    suspend fun addInvestment(investment: Investment)
    suspend fun updateInvestment(investment: Investment)
    suspend fun deleteInvestment(investment: Investment)
    suspend fun deactivateInvestment(id: String)
    suspend fun updateValue(id: String, value: Long)
}
