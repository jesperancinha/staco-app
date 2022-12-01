package org.jesperancinha.enterprise.staco.blocking.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RedirectController {
    @GetMapping("/")
    fun void(httpServletResponse: HttpServletResponse) {
        httpServletResponse.addHeader("Location", "http://localhost:4200/search")
        httpServletResponse.status = 302
    }
}