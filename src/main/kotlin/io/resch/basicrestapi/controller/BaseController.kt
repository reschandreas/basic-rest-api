package io.resch.basicrestapi.controller

import io.resch.basicrestapi.controller.utils.CachedRole
import io.resch.basicrestapi.service.ApiUserService
import org.springframework.security.core.context.SecurityContextHolder

class BaseController(protected val userService: ApiUserService) {
    private val cache = mutableMapOf<String, CachedRole>()

    private val roles: CachedRole
        get() {
            val username = SecurityContextHolder.getContext().authentication.principal as String
            val cached = cache.getOrDefault(username, CachedRole(0, mutableListOf()))
            if (cached.timestamp <= System.currentTimeMillis() - TTL) {
                cache[username] = CachedRole(userService.getRoles())
            }
            return cache[username]!!
        }

    protected fun hasAccess(vararg roles: String): Boolean {
        val cachedRole = this.roles
        for (s in roles) {
            for (role in cachedRole.roles) {
                if (s == role.name) {
                    return true
                }
            }
        }
        return false
    }

    companion object {

        private val TTL = 60 * 1 * 1000
    }
}
