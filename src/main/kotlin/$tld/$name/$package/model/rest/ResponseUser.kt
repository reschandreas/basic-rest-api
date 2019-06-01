package $tld.$name.$package.model.rest

import $tld.$name.$package.auth.SecurityConstants.TOKEN_PREFIX
import $tld.$name.$package.model.ApiUser
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
