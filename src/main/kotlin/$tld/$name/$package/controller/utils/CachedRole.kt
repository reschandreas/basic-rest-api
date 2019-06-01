package $tld.$name.$package.controller.utils

import $tld.$name.$package.model.Role

data class CachedRole(val timestamp: Long = System.currentTimeMillis(), val roles: List<Role> = listOf()) {
    constructor(roles: List<Role>) : this(System.currentTimeMillis(), roles)
}