package io.resch.basicrestapi.service;

import io.resch.basicrestapi.model.ApiUser
import io.resch.basicrestapi.model.Role
import io.resch.basicrestapi.repository.ApiUserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ApiUserService(private val applicationUserRepository: ApiUserRepository) : UserDetailsService {

    fun getRoles(): List<Role> {
        val username = SecurityContextHolder.getContext().authentication.principal as String
        val user = getByUsername(username)
        return user?.roles ?: emptyList()
    }

    fun getByUsername(username: String): ApiUser? {
        return applicationUserRepository.findByUsername(username)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        return applicationUserRepository.findByUsername(s)
    }
}
