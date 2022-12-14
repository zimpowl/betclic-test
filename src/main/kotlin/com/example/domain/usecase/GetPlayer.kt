package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import com.example.domain.entity.Player
import java.lang.Exception

sealed class GetPlayerResponse {
    data class OnSuccess(val player: Player) : GetPlayerResponse()
    object OnFailure : GetPlayerResponse()
}

class GetPlayer(
    private val repository: PlayerDao
) : UseCaseWithArguments<Int, GetPlayerResponse>() {

    override suspend fun doJob(playerId: Int): GetPlayerResponse {
        return try {
            repository.player(playerId)?.let { player ->
                GetPlayerResponse.OnSuccess(player)
            } ?: GetPlayerResponse.OnFailure
        } catch (e: Exception) {
            GetPlayerResponse.OnFailure
        }
    }
}