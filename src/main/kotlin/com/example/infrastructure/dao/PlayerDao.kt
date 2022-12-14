package com.example.infrastructure.dao

import com.example.domain.entity.Player

interface PlayerDao {
    suspend fun addNewPlayer(pseudo: String): Boolean
    suspend fun editPlayerScore(id: Int, score: Int): Boolean
    suspend fun player(id: Int): Player?
    suspend fun allPlayers(): List<Player>
    suspend fun deleteAllPlayers(): Boolean
}