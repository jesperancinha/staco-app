package org.jesperancinha.enterprise.engine.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class LoginService(
    val jdbcTemplate: JdbcTemplate,
    @Autowired(required = false)
    val tokenStore: TokenStore?
) {
    fun logout(request: HttpServletRequest) {
        SecurityContextLogoutHandler().logout(request, null, null)
        val authorization = request.getHeader("Authorization")
        val token = authorization.split(" ")[1]
        jdbcTemplate.update("delete from oauth_access_token where token_id = ?", token);
        jdbcTemplate.update("delete from oauth_refresh_token where token_id = ?", token);
        val tokenValue: String = authorization.replace("Bearer", "").trim()
        val accessToken: org.springframework.security.oauth2.common.OAuth2AccessToken? =
            tokenStore!!.readAccessToken(tokenValue)
        tokenStore!!.removeAccessToken(accessToken)
        val refreshToken: org.springframework.security.oauth2.common.OAuth2RefreshToken? =
            accessToken?.getRefreshToken()
        tokenStore!!.removeRefreshToken(refreshToken)
    }
}