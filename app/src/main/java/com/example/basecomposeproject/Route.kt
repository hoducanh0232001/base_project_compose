package com.example.basecomposeproject

import kotlinx.serialization.Serializable

object Route {

    @Serializable
    data object Splash

    @Serializable
    data object AttentionUserApp

    @Serializable
    data object Menu

    @Serializable
    data object Setting

    @Serializable
    data object Operation

    @Serializable
    data object InfoMachine

    @Serializable
    data object Device


}
