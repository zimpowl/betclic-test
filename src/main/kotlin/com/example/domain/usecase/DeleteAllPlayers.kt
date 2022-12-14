package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import java.lang.Exception

sealed class DeleteAllPlayersResponse {
    object OnSuccess : DeleteAllPlayersResponse()
    object OnFailure : DeleteAllPlayersResponse()
}

class DeleteAllPlayers(
    private val repository: PlayerDao
) : UseCaseWithoutArguments<DeleteAllPlayersResponse>() {

    override suspend fun doJob(): DeleteAllPlayersResponse {
        return try {
            if (repository.deleteAllPlayers()) {
                DeleteAllPlayersResponse.OnSuccess
            } else {
                DeleteAllPlayersResponse.OnFailure
            }
        } catch (e: Exception) {
            DeleteAllPlayersResponse.OnFailure
        }
    }
}