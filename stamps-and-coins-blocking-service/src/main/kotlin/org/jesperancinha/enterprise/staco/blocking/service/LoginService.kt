package org.jesperancinha.enterprise.staco.blocking.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Service

@Service
class LoginService(
    val authorizationService: OAuth2AuthorizationService?
) {
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {
        SecurityContextLogoutHandler().logout(request, response, null)
        val authorization = request.getHeader("Authorization")
        val tokenValue: String = authorization.replace("Bearer", "").trim()
        authorizationService?.let { service ->
            service.findByToken(tokenValue, OAuth2TokenType.ACCESS_TOKEN)
                ?.let { service.remove(it) }
        }
    }
}