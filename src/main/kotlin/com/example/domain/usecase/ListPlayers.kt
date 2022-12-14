package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import com.example.domain.entity.Player
import java.lang.Exception

sealed class ListPlayersResponse {
    data class OnSuccess(val listPlayers: List<Player>) : ListPlayersResponse()
    object OnFailure : ListPlayersResponse()
}

class ListPlayers(
    private val repository: PlayerDao
) : UseCaseWithoutArguments<ListPlayersResponse>() {

    override suspend fun doJob(): ListPlayersResponse {
        return try {
            ListPlayersResponse.OnSuccess(
                repository.allPlayers()
            )
        } catch (e: Exception) {
            ListPlayersResponse.OnFailure
        }
    }
}