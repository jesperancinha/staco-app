package org.jesperancinha.enterprise.staco.jpa.security.local

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@Profile("!prod && !localprod && !test")
class ConfigurationHandler(
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication
    ) {
        httpServletResponse.sendRedirect("/search")
    }
}