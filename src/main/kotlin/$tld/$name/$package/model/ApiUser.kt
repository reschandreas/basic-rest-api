package $tld.$name.$package.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.ArrayList
import javax.persistence.*

@Entity
@javax.persistence.Table(name = "api_user")
class ApiUser : UserDetails {
    @Id
    @GeneratedValue
    val id: Long = 0
    private var username: String? = null
    private var password: String? = null
    var isLocked = false

    @OneToMany(fetch = FetchType.EAGER)
    var roles: List<Role> = ArrayList<Role>()
        private set

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return !isLocked
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val list = ArrayList<GrantedAuthority>()

        for (role in roles) {
            list.add(SimpleGrantedAuthority(role.name))
        }

        return list
    }
}