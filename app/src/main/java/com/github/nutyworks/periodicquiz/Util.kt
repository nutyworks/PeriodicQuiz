package com.github.nutyworks.periodicquiz

import android.annotation.SuppressLint
import kotlin.reflect.full.memberProperties

fun String.screamingSnakeCaseToNormal(): String =
    this.replace("_", " ").toLowerCase().capitalize()

fun String.screamingSnakeCaseToSnakeCase(): String = this.toLowerCase()

inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
    val props = T::class.memberProperties.associateBy { it.name }
    return props.keys.associateWith { props[it]?.get(this) }
}
