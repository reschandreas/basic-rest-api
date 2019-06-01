package io.resch.basicrestapi.model.rest

import io.resch.basicrestapi.auth.SecurityConstants.TOKEN_PREFIX
import io.resch.basicrestapi.model.ApiUser
import java.io.Serializable
import java.util.ArrayList


class ResponseUser(user: ApiUser, token: String) : Serializable {

    var username: String? = null
    var roles = ArrayList<String>()
    var token: String? = null

    init {
        this.username = user.getUsername()
        for (role in user.roles) {
            role.name?.let { this.roles.add(it) }
        }
        this.token = TOKEN_PREFIX + token
    }
}
