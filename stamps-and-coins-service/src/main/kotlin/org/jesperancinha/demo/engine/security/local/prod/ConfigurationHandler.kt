package org.jesperancinha.demo.engine.security.local.prod

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@Profile("localprod && !test")
class ConfigurationHandler(
) : AuthenticationSuccessHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        httpServletRequest: HttpServletRequest?,
        httpServletResponse: HttpServletResponse,
        authentication: Authentication
    ) {
        httpServletResponse.sendRedirect("/search")
    }
}