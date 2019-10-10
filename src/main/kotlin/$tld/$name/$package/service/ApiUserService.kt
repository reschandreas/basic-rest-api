package $tld.$name.$package.service;

import $tld.$name.$package.model.ApiUser
import $tld.$name.$package.model.Role
import $tld.$name.$package.repository.ApiUserRepository
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

    fun save(user: ApiUser): Boolean {
        return applicationUserRepository.save(user)
    }

    fun getByUsername(username: String): ApiUser? {
        return applicationUserRepository.findByUsername(username)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        return applicationUserRepository.findByUsername(s)
    }
}
