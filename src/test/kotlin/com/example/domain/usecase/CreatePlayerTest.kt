package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.*

@RunWith(MockitoJUnitRunner::class)
class CreatePlayerTest {

    @InjectMocks
    private lateinit var useCase: CreatePlayer

    @Mock
    private lateinit var repository: PlayerDao

    @Test
    fun `CreatePlayer - When addNewPlayer return true Should return OnSuccess`() {
        runBlocking {
            // Given
            given(repository.addNewPlayer("Player One")).willReturn(true)

            // When
            val result = useCase.doJob("Player One")

            // Then
            Assertions.assertEquals(result, CreatePlayerResponse.OnSuccess)
        }
    }

    @Test
    fun `CreatePlayer - When addNewPlayer return false Should return OnFailure`() {
        runBlocking {
            // Given
            given(repository.addNewPlayer("Player One")).willReturn(false)

            // When
            val result = useCase.doJob("Player One")

            // Then
            Assertions.assertEquals(result, CreatePlayerResponse.OnFailure)
        }
    }

}