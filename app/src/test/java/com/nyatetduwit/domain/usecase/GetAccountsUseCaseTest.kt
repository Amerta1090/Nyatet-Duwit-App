package com.nyatetduwit.domain.usecase.account

import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.AccountType
import com.nyatetduwit.domain.repository.AccountRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetAccountsUseCaseTest {

    private lateinit var repository: AccountRepository
    private lateinit var getAccountsUseCase: GetAccountsUseCase

    @Before
    fun setup() {
        repository = mock()
        getAccountsUseCase = GetAccountsUseCase(repository)
    }

    @Test
    fun invoke_shouldReturnListOfAccounts() {
        val expectedAccounts = listOf(
            Account(
                id = "1",
                name = "Cash",
                type = AccountType.CASH,
                balance = 100000,
                icon = "payments",
                color = "#4F6B4E",
                isHidden = false,
                orderIndex = 0,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
            )
        )
        whenever(repository.getAllAccounts()).thenReturn(flowOf(expectedAccounts))

        runBlocking {
            val result = getAccountsUseCase()
            assertNotNull(result)
            verify(repository).getAllAccounts()
        }
    }
}
