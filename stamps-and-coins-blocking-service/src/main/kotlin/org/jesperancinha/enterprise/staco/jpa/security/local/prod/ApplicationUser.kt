package org.jesperancinha.enterprise.staco.jpa.security.local.prod

import org.springframework.context.annotation.Profile
import org.springframework.lang.Nullable
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
@Profile("localprod && !test")
class ApplicationUser {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column
    var email: String? = null

    @Column
    var name: String? = null

    @Column
    var password: String? = null

    @Column
    var role: String? = null

    @get:Nullable
    @Column
    @Nullable
    var date: Timestamp? = null
}
