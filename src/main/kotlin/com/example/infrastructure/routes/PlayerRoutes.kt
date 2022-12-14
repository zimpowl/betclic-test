package com.example.infrastructure.routes

import com.example.domain.usecase.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.java.KoinJavaComponent.inject

fun Route.playerRouting() {

    val listPlayers: ListPlayers by inject(ListPlayers::class.java)
    val createPlayer: CreatePlayer by inject(CreatePlayer::class.java)
    val getPlayer: GetPlayer by inject(GetPlayer::class.java)
    val deleteAllPlayers: DeleteAllPlayers by inject(DeleteAllPlayers::class.java)
    val updateScorePlayer: UpdateScorePlayer by inject(UpdateScorePlayer::class.java)

    route("/player") {
        get {
            when (val result = listPlayers.doJob()) {
                is ListPlayersResponse.OnSuccess -> call.respond(result.listPlayers)
                is ListPlayersResponse.OnFailure -> call.respond(HttpStatusCode.NotFound, "listPlayers")
            }
        }

        post("{pseudo?}") {
            val pseudo = call.parameters.getOrFail("pseudo")
            when (createPlayer.doJob(pseudo)) {
                is CreatePlayerResponse.OnSuccess -> call.respond(
                    HttpStatusCode.OK,
                    "Player has been registered"
                )
                is CreatePlayerResponse.OnFailure -> call.respond(HttpStatusCode.NotFound, "createPlayer")
            }
        }

        put("{id?}/edit/{score?}") {
            val id = call.parameters.getOrFail<Int>("id")
            val score = call.parameters.getOrFail<Int>("score")
            when (updateScorePlayer.doJob(Pair(id, score))) {
                is UpdateScorePlayerResponse.OnSuccess -> call.respond(
                    HttpStatusCode.OK,
                    "Player score has been updated"
                )
                is UpdateScorePlayerResponse.OnFailure -> call.respond(HttpStatusCode.NotFound, "createPlayer")
            }
        }

        get("{id?}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            when (val result = getPlayer.doJob(id)) {
                is GetPlayerResponse.OnSuccess -> call.respond(result.player)
                is GetPlayerResponse.OnFailure -> call.respond(HttpStatusCode.NotFound, "getPlayer")
            }
        }

        delete {
            when (deleteAllPlayers.doJob()) {
                is DeleteAllPlayersResponse.OnSuccess -> call.respond(HttpStatusCode.OK, "All users have been deleted")
                is DeleteAllPlayersResponse.OnFailure -> call.respond(HttpStatusCode.NotFound, "listPlayers")
            }
        }
    }
}