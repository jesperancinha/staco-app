package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import jakarta.persistence.*
import org.springframework.context.annotation.Profile
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "users")
@Profile("localprod && !test")
class ApplicationUser(
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID? = null,

    @Column
    val email: String? = null,

    @Column
    var name: String? = null,

    @Column
    var password: String? = null,

    @Column
    val role: String? = null,

    @Column
    var date: Timestamp? = null
)
