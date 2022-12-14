package com.example.infrastructure.repository

import com.example.infrastructure.repository.DatabaseFactory.dbQuery
import com.example.domain.entity.Player
import com.example.domain.entity.Players
import com.example.infrastructure.dao.PlayerDao
import org.jetbrains.exposed.sql.*

class PlayerRepository : PlayerDao {

    private fun resultRowToPlayer(index: Int, row: ResultRow) = Player(
        id = row[Players.id],
        pseudo = row[Players.pseudo],
        score = row[Players.score],
        ranking = index + 1
    )

    override suspend fun addNewPlayer(pseudo: String): Boolean = dbQuery {
        val insertStatement = Players.insert {
            it[Players.pseudo] = pseudo
            it[score] = 0
        }
        insertStatement.resultedValues?.singleOrNull() != null
    }

    override suspend fun editPlayerScore(id: Int, score: Int): Boolean = dbQuery {
        val updateStatement = Players.update({ Players.id eq id }) {
            it[Players.score] = score
        }
        updateStatement > 0
    }

    override suspend fun player(id: Int): Player? = dbQuery {
        allPlayers().firstOrNull { it.id == id }
    }

    override suspend fun allPlayers(): List<Player> = dbQuery {
        Players.selectAll().orderBy(Players.score, SortOrder.DESC).mapIndexed(::resultRowToPlayer)
    }

    override suspend fun deleteAllPlayers(): Boolean = dbQuery {
        Players.deleteAll() > 0
    }
}