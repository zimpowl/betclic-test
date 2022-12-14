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
class ListPlayersTest {

    @InjectMocks
    private lateinit var useCase: ListPlayers

    @Mock
    private lateinit var repository: PlayerDao

    private val PLAYER_LIST = listOf(
        Player(
            id = 1,
            pseudo = "Player One",
            score = 5,
            ranking = 1
        ),
        Player(
            id = 2,
            pseudo = "Player Two",
            score = 2,
            ranking = 2
        ),
    )

    @Test
    fun `ListPlayersTest - When allPlayers found Should return OnSuccess Player List`() {
        runBlocking {
            // Given
            BDDMockito.given(repository.allPlayers()).willReturn(PLAYER_LIST)

            // When
            val result = useCase.doJob()

            // Then
            assertEquals(result, ListPlayersResponse.OnSuccess(PLAYER_LIST))
        }
    }

    @Test
    fun `ListPlayersTest - When allPlayers not found return Should return OnFailure`() {
        runBlocking {
            // When
            val result = useCase.doJob()

            // Then
            assertEquals(result, ListPlayersResponse.OnFailure)
        }
    }
}