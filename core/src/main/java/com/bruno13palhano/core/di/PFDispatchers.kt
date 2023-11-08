package com.bruno13palhano.core.di

import javax.inject.Qualifier

@Qualifier
@Retention
annotation class Dispatcher(val dispatcher: PFDispatchers)

enum class PFDispatchers {
    IO,
    DEFAULT
}