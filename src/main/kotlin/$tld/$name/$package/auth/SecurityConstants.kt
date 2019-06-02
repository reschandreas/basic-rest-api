package $tld.$name.$package.auth

internal object SecurityConstants {
    val SECRET = "$secret"
    val EXPIRATION_TIME: Long = 84600
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    val SIGN_UP_URL = "/auth/sign-up"
    val LOGIN_URL = "/auth/login"
}