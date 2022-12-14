package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import kotlin.test.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteAllPlayersTest {

    @InjectMocks
    private lateinit var useCase: DeleteAllPlayers

    @Mock
    private lateinit var repository: PlayerDao

    @Test
    fun `DeleteAllPlayers - When deleteAllPlayers return true Should return OnSuccess`() {
        runBlocking {
            // Given
            BDDMockito.given(repository.deleteAllPlayers()).willReturn(true)

            // When
            val result = useCase.doJob()

            // Then
            assertEquals(result, DeleteAllPlayersResponse.OnSuccess)
        }
    }

    @Test
    fun `DeleteAllPlayers - When deleteAllPlayers return false Should return OnFailure`() {
        runBlocking {
            // Given
            BDDMockito.given(repository.deleteAllPlayers()).willReturn(false)

            // When
            val result = useCase.doJob()

            // Then
            assertEquals(result, DeleteAllPlayersResponse.OnFailure)
        }
    }
}