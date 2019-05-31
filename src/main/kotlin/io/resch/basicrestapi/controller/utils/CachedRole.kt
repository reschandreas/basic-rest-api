package io.resch.basicrestapi.controller.utils

import io.resch.basicrestapi.model.Role

data class CachedRole(val timestamp: Long = System.currentTimeMillis(), val roles: List<Role> = listOf()) {
    constructor(roles: List<Role>) : this(System.currentTimeMillis(), roles)
}