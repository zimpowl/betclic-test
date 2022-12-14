package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import java.lang.Exception

sealed class CreatePlayerResponse {
    object OnSuccess : CreatePlayerResponse()
    object OnFailure : CreatePlayerResponse()
}

class CreatePlayer(
    private val repository: PlayerDao
) : UseCaseWithArguments<String, CreatePlayerResponse>() {

    override suspend fun doJob(playerPseudo: String): CreatePlayerResponse {
        return try {
            if (repository.addNewPlayer(playerPseudo)) {
                CreatePlayerResponse.OnSuccess
            } else {
                CreatePlayerResponse.OnFailure
            }
        } catch (e: Exception) {
            CreatePlayerResponse.OnFailure
        }
    }
}