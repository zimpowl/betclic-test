package com.example

import com.example.infrastructure.repository.DatabaseFactory
import com.example.infrastructure.dao.PlayerDao
import com.example.infrastructure.repository.PlayerRepository
import com.example.infrastructure.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.domain.usecase.*
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val appModule = module {
    single<PlayerDao> { PlayerRepository() }
    single { ListPlayers(get()) }
    single { CreatePlayer(get()) }
    single { DeleteAllPlayers(get()) }
    single { GetPlayer(get()) }
    single { UpdateScorePlayer(get()) }
}

fun main() {
    embeddedServer(Netty, port = 9090, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    startKoin {
        modules(appModule)
    }

    DatabaseFactory.init()

    configureRouting()
}
