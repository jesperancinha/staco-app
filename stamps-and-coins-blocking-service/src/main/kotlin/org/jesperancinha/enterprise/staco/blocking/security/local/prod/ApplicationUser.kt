package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import org.springframework.context.annotation.Profile
import org.springframework.lang.Nullable
import java.sql.Timestamp
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
@Profile("localprod && !test")
class ApplicationUser {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID? = null

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
