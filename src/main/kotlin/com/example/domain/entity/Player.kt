package com.example.domain.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Player(
    val id: Int,
    val pseudo: String,
    val score: Int = 0,
    val ranking: Int = 0
)

object Players : Table() {
    val id = integer("id").autoIncrement()
    val pseudo = varchar("pseudo", 128)
    val score = integer("score")

    override val primaryKey = PrimaryKey(id)
}