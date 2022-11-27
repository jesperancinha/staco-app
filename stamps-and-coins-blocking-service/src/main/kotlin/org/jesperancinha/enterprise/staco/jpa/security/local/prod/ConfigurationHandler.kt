package org.jesperancinha.enterprise.staco.jpa.security.local.prod

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

@Configuration
@Profile("localprod && !test")
class ConfigurationHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication
    ) {
        httpServletResponse.sendRedirect("/search")
    }
}