package io.resch.basicrestapi.auth

internal object SecurityConstants {
    val SECRET = "0B252F7A9D9A43FB603DA9F0D392B974F89FFAD6D3B8DA014B785AB3DE3D97EC"
    val EXPIRATION_TIME: Long = 84600
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    val SIGN_UP_URL = "/users/sign-up"
    val LOGIN_URL = "/auth/login"
}