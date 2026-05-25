package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.InvestmentDao
import com.nyatetduwit.data.local.entity.InvestmentEntity
import com.nyatetduwit.domain.model.Investment
import com.nyatetduwit.domain.model.InvestmentType
import com.nyatetduwit.domain.repository.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class InvestmentRepositoryImpl @Inject constructor(
    private val investmentDao: InvestmentDao,
) : InvestmentRepository {

    override fun getAllActiveInvestments(): Flow<List<Investment>> {
        return investmentDao.getAllActiveInvestments().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllInvestments(): Flow<List<Investment>> {
        return investmentDao.getAllInvestments().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getInvestmentById(id: String): Investment? {
        return investmentDao.getInvestmentById(id)?.toDomain()
    }

    override fun getTotalInvestmentValue(): Flow<Long> {
        return investmentDao.getTotalInvestmentValue()
    }

    override fun getTotalCostBasis(): Flow<Long> {
        return investmentDao.getTotalCostBasis()
    }

    override suspend fun addInvestment(investment: Investment) {
        investmentDao.insert(investment.toEntity())
    }

    override suspend fun updateInvestment(investment: Investment) {
        investmentDao.update(investment.toEntity())
    }

    override suspend fun deleteInvestment(investment: Investment) {
        investmentDao.delete(investment.toEntity())
    }

    override suspend fun deactivateInvestment(id: String) {
        investmentDao.deactivate(id)
    }

    override suspend fun updateValue(id: String, value: Long) {
        investmentDao.updateValue(id, value)
    }

    private fun InvestmentEntity.toDomain(): Investment {
        return Investment(
            id = id,
            name = name,
            type = InvestmentType.fromValue(type),
            currentValue = currentValue,
            costBasis = costBasis,
            currencyCode = currencyCode,
            accountId = accountId,
            notes = notes,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun Investment.toEntity(): InvestmentEntity {
        return InvestmentEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            name = name,
            type = type.value,
            currentValue = currentValue,
            costBasis = costBasis,
            currencyCode = currencyCode,
            accountId = accountId,
            notes = notes,
            isActive = isActive,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }
}
