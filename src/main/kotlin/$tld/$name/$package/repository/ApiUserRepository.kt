package $tld.$name.$package.repository

import $tld.$name.$package.model.ApiUser
import org.springframework.data.jpa.repository.JpaRepository

interface ApiUserRepository : JpaRepository<ApiUser, Long> {
    fun findByUsername(username: String): ApiUser
}