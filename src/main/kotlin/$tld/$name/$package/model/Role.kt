package $tld.$name.$package.model

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "roles")
class Role : GrantedAuthority {

    @Id
    @Column(columnDefinition = "varchar(50) not null", unique = true)
    var name: String? = null

    override fun getAuthority(): String? {
        return name
    }

    override fun toString(): String {
        return "Role{" +
                "name='" + name + '\''.toString() +
                '}'.toString()
    }
}
