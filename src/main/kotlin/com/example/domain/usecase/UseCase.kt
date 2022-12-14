package com.example.domain.usecase

sealed class UseCase<R: Any>

abstract class UseCaseWithoutArguments<R: Any>: UseCase<R>() {
    abstract suspend fun doJob(): R
}

abstract class UseCaseWithArguments<A: Any, R : Any>: UseCase<R>() {
    abstract suspend fun doJob(arg: A): R
}