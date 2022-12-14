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
class UpdateScorePlayerTest {

    @InjectMocks
    private lateinit var useCase: UpdateScorePlayer

    @Mock
    private lateinit var repository: PlayerDao

    @Test
    fun `UpdateScorePlayer - When deleteAllPlayers return true Should return OnSuccess`() {
        runBlocking {
            // Given
            BDDMockito.given(repository.editPlayerScore(1, 5)).willReturn(true)

            // When
            val result = useCase.doJob(Pair(1, 5))

            // Then
            assertEquals(result, UpdateScorePlayerResponse.OnSuccess)
        }
    }

    @Test
    fun `UpdateScorePlayer - When deleteAllPlayers return false Should return OnFailure`() {
        runBlocking {
            // Given
            BDDMockito.given(repository.editPlayerScore(1, 5)).willReturn(false)

            // When
            val result = useCase.doJob(Pair(1, 5))

            // Then
            assertEquals(result, UpdateScorePlayerResponse.OnFailure)
        }
    }
}