package $tld.$name.$package.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import $tld.$name.$package.auth.SecurityConstants.EXPIRATION_TIME
import $tld.$name.$package.auth.SecurityConstants.SECRET
import $tld.$name.$package.model.ApiUser
import $tld.$name.$package.model.rest.ResponseUser
import $tld.$name.$package.repository.ApiUserRepository
import $tld.$name.$package.service.ApiUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/auth")
class ApiUserController(private val apiUserRepository: ApiUserRepository,
                        private val bCryptPasswordEncoder: BCryptPasswordEncoder, userService: ApiUserService) : BaseController(userService) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody user: ApiUser) {
        //if (super.hasAccess("ADMIN")) {
            user.setPassword(bCryptPasswordEncoder.encode(user.password))
            apiUserRepository.save(user)
        //}
    }

    @PostMapping("/login")
    @Throws(Exception::class)
    fun login(@RequestBody user: ApiUser): ResponseEntity<ResponseUser> {
        val requestedUser = user.username?.let { apiUserRepository.findByUsername(it) }
                ?: throw Exception("User not found")

        if (requestedUser.isLocked) {
            throw Exception("User is locked")
        }

        if (!bCryptPasswordEncoder.matches(user.password, requestedUser.password)) {
            throw Exception("User and/or Password incorrect")
        }
        val expires = Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000)

        val token = JWT.create()
                .withSubject(user.username)
                .withExpiresAt(expires).sign(Algorithm.HMAC512(SECRET))
        return ResponseEntity.ok<ResponseUser>(ResponseUser(requestedUser, token))
    }
}