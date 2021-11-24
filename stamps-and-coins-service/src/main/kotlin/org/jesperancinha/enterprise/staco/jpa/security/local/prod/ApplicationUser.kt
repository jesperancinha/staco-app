package org.jesperancinha.enterprise.staco.jpa.security.local.prod

import org.springframework.context.annotation.Profile
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.lang.Nullable
import java.sql.Timestamp

@Table("users")
@Profile("localprod && !test")
class ApplicationUser {
    @Id
    @Column
    var id: Long? = null

    @Column
    var email: String? = null

    @Column
    var name: String? = null

    @Column
    var password: String? = null

    @Column
    var role: String? = null

    @Column
    @Nullable
    var date: Timestamp? = null
}
