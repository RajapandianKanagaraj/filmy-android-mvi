package com.filmy.tracking.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Trackable(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val metadata: String = "",
)