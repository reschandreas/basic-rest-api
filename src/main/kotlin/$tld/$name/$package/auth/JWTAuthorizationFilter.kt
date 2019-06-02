package $tld.$name.$package.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import $tld.$name.$package.auth.SecurityConstants.HEADER_STRING
import $tld.$name.$package.auth.SecurityConstants.SECRET
import $tld.$name.$package.auth.SecurityConstants.TOKEN_PREFIX
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.ArrayList
import org.springframework.http.HttpStatus
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.exceptions.SignatureVerificationException
import org.springframework.security.core.GrantedAuthority

class JWTAuthorizationFilter
/**
 * Instantiates a new Jwt authorization filter.
 *
 * @param authenticationManager the authentication manager
 */
(authenticationManager: AuthenticationManager) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
            request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        // read the request header and extract the JWT token
        val header = request.getHeader(HEADER_STRING)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        try {
            // validate the JWT Token
            val authentication = getAuthentication(request)
            // if user is valid with token allow priced the request with adding user to security context
            SecurityContextHolder.getContext().authentication = authentication
            chain.doFilter(request, response)
        } catch (e: SignatureVerificationException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write("Authentication error, SignatureVerification fail.")
        } catch (e: TokenExpiredException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write("Authentication error, The Token's Expired.")
        }

    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            // parse the token.
            val user = JWT.require(Algorithm.HMAC512(SECRET))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .subject

            return if (user != null) {
                UsernamePasswordAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
            } else null
        }
        return null
    }
}
