package com.filmy.tracking

import kotlinx.serialization.Serializable
@Serializable
data class Environment(
    val customerGuid: String,
    val deviceGuid: String,
    val os: String,
    val device: String,
)
