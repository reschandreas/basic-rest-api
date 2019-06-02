package $tld.$name.$package.auth

import $tld.$name.$package.repository.ApiUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val apiUserRepository: ApiUserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val applicationUser = apiUserRepository.findByUsername(username)

        return User(applicationUser.username, applicationUser.password, applicationUser.authorities)
    }
}