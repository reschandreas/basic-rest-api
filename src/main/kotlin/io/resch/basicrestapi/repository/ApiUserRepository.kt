package io.resch.basicrestapi.repository

import io.resch.basicrestapi.model.ApiUser
import org.springframework.data.jpa.repository.JpaRepository

interface ApiUserRepository : JpaRepository<ApiUser, Long> {
    fun findByUsername(username: String): ApiUser
}