package com.example.domain.usecase

import com.example.domain.entity.Player
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
class GetPlayerTest {

    @InjectMocks
    private lateinit var useCase: GetPlayer

    @Mock
    private lateinit var repository: PlayerDao

    private val PLAYER_ONE = Player(
        id = 1,
        pseudo = "Player One",
        score = 0,
        ranking = 1
    )

    @Test
    fun `GetPlayerTest - When player 1 found Should return OnSuccess Player One`() {
        runBlocking {
            // Given
            BDDMockito.given(repository.player(1)).willReturn(PLAYER_ONE)

            // When
            val result = useCase.doJob(1)

            // Then
            assertEquals(result, GetPlayerResponse.OnSuccess(PLAYER_ONE))
        }
    }

    @Test
    fun `GetPlayerTest - When player 1 not found Should return OnFailure`() {
        runBlocking {
            // When
            val result = useCase.doJob(1)

            // Then
            assertEquals(result, GetPlayerResponse.OnFailure)
        }
    }
}