package com.example

import com.example.domain.entity.Player
import com.example.domain.entity.Players
import com.example.domain.usecase.*
import com.example.infrastructure.dao.PlayerDao
import com.example.infrastructure.repository.PlayerRepository
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.infrastructure.routes.configureRouting
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApplicationTest : KoinTest {

    private val database = Database.connect("jdbc:h2:mem:db_test;DB_CLOSE_DELAY=-1;IGNORECASE=true;")

    @Before
    fun before() {
        startKoin { }

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

    @Test
    fun `Application - Player`() = testApplication {
        application {
            configureRouting()
        }

        // Create player
        client.post("/player/pseudo").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Player has been registered", bodyAsText())
        }

        // Get all players
        client.get("/player").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("[{\"id\":1,\"pseudo\":\"pseudo\",\"score\":0,\"ranking\":1}]", bodyAsText())
        }

        // Edit score player
        client.put("/player/1/edit/5").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Player score has been updated", bodyAsText())
        }

        // Get player
        client.get("/player?id=1").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("[{\"id\":1,\"pseudo\":\"pseudo\",\"score\":5,\"ranking\":1}]", bodyAsText())
        }

        // Delete all player
        client.delete("/player").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("All users have been deleted", bodyAsText())
        }
    }
}