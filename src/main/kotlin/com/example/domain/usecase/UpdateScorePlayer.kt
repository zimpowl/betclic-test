package com.example.domain.usecase

import com.example.infrastructure.dao.PlayerDao
import java.lang.Exception

sealed class UpdateScorePlayerResponse {
    object OnSuccess : UpdateScorePlayerResponse()
    object OnFailure : UpdateScorePlayerResponse()
}

class UpdateScorePlayer(
    private val repository: PlayerDao
) : UseCaseWithArguments<Pair<Int, Int>, UpdateScorePlayerResponse>() {

    override suspend fun doJob(scorePlayer: Pair<Int, Int>): UpdateScorePlayerResponse {
        return try {
            if (repository.editPlayerScore(scorePlayer.first, scorePlayer.second)) {
                UpdateScorePlayerResponse.OnSuccess
            } else {
                UpdateScorePlayerResponse.OnFailure
            }
        } catch (e: Exception) {
            UpdateScorePlayerResponse.OnFailure
        }
    }
}