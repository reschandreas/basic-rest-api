package $tld.$name.$package.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.fasterxml.jackson.databind.ObjectMapper
import $tld.$name.$package.auth.SecurityConstants.EXPIRATION_TIME
import $tld.$name.$package.auth.SecurityConstants.HEADER_STRING
import $tld.$name.$package.auth.SecurityConstants.SECRET
import $tld.$name.$package.auth.SecurityConstants.TOKEN_PREFIX
import $tld.$name.$package.model.ApiUser
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter : UsernamePasswordAuthenticationFilter {

    private val authManager: AuthenticationManager

    constructor(authenticationManager: AuthenticationManager) : super() {
        this.authManager = authenticationManager
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest,
                                       res: HttpServletResponse?): Authentication {
        try {
            val creds = ObjectMapper()
                    .readValue(req.inputStream, ApiUser::class.java)

            return authManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            creds.username,
                            creds.password,
                            creds.authorities)
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest,
                                          res: HttpServletResponse,
                                          chain: FilterChain?,
                                          auth: Authentication) {
        val expires = Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000)
        val token = JWT.create()
                .withSubject((auth.principal as User).username)
                .withExpiresAt(expires)
                .withNotBefore(Date())
                .sign(HMAC512(SECRET))
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}