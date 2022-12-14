package com.example.infrastructure.repository

import com.example.domain.entity.Player
import com.example.domain.entity.Players
import com.example.domain.usecase.*
import com.example.infrastructure.dao.PlayerDao
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class PlayerRepositoryTest: KoinTest {

    private val database = Database.connect("jdbc:h2:mem:db_test;DB_CLOSE_DELAY=-1;IGNORECASE=true;")
    private val repository: PlayerDao by inject()

    @Before
    fun before() {
        startKoin {  }

        loadKoinModules(
            module {
                single<PlayerDao> { PlayerRepository() }
                single { ListPlayers(get()) }
                single { CreatePlayer(get()) }
                single { DeleteAllPlayers(get()) }
                single { GetPlayer(get()) }
                single { UpdateScorePlayer(get()) }
            }
        )

        transaction(database) {
            SchemaUtils.drop(Players)
            SchemaUtils.create(Players)
        }
    }

    @After
    fun after() {
        TransactionManager.closeAndUnregister(database)
        stopKoin()
    }

    private val playerOne = Player(
        id = 1,
        pseudo = "Player One",
        score = 5,
        ranking = 2
    )

    private val playerTwo = Player(
        id = 2,
        pseudo = "Player Two",
        score = 3,
        ranking = 3
    )

    private val playerThree = Player(
        id = 3,
        pseudo = "Player Three",
        score = 8,
        ranking = 1
    )

    @Test
    fun `Player - test`() {
        runBlocking {
            // Add new player
            assertTrue(repository.addNewPlayer("Player One"))
            assertTrue(repository.addNewPlayer("Player Two"))
            assertTrue(repository.addNewPlayer("Player Three"))
            // Edit player score
            assertTrue(repository.editPlayerScore(1, 5))
            assertTrue(repository.editPlayerScore(2, 3))
            assertTrue(repository.editPlayerScore(3, 8))
            // Get player with ranking
            assertEquals(repository.player(3), playerThree)
            // Get all player
            assertEquals(repository.allPlayers(), listOf(playerThree, playerOne, playerTwo))
            // Delete all player
            assertTrue(repository.deleteAllPlayers())
        }
    }
}