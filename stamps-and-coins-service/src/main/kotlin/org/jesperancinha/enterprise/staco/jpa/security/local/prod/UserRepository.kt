package org.jesperancinha.enterprise.staco.jpa.security.local.prod

import org.springframework.context.annotation.Profile
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Profile("localprod && !test")
interface UserRepository : CoroutineCrudRepository<ApplicationUser?, String?> {
    fun findUserByName(name: String?): ApplicationUser?
}