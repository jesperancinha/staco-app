package org.jesperancinha.enterprise.staco.blocking.security.local.prod

import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository

@Profile("localprod && !test")
interface UserRepository : JpaRepository<ApplicationUser?, String?> {
    fun findUserByName(name: String?): ApplicationUser?
}