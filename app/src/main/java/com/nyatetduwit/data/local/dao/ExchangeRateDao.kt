package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateDao {

    @Query("SELECT * FROM exchange_rates ORDER BY currency_code ASC")
    fun getAllRates(): Flow<List<ExchangeRateEntity>>

    @Query("SELECT * FROM exchange_rates WHERE currency_code = :currencyCode")
    suspend fun getRate(currencyCode: String): ExchangeRateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRate(rate: ExchangeRateEntity)

    @Query("DELETE FROM exchange_rates WHERE currency_code = :currencyCode")
    suspend fun deleteRate(currencyCode: String)

    @Query("DELETE FROM exchange_rates")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rates: List<ExchangeRateEntity>)
}
