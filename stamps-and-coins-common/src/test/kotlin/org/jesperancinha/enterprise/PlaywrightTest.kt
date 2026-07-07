package org.jesperancinha.enterprise

import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class PlaywrightTest {
    lateinit var context: BrowserContext
    lateinit var page: Page
    val playwright = Playwright.create()
    val browser = playwright.chromium().launch(
        BrowserType.LaunchOptions()
            .setExecutablePath(Paths.get("/usr/bin/google-chrome"))
    )

    @BeforeEach
    fun createContextAndPage() {

        context = browser.newContext()
        page = context.newPage()
    }

    @AfterEach
    fun closeContext() {
        if (::context.isInitialized) {
            context.close()
            playwright.close()
        }
    }

    @Test
    fun shouldClickButton() {
        page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>")
        page.locator("button").click()
        page.evaluate("result") shouldBe "Clicked"
    }

    @Test
    fun shouldCheckTheBox() {
        page.setContent("<input id='checkbox' type='checkbox'></input>")
        page.locator("input").check()
        (page.evaluate("() => window['checkbox'].checked") as Boolean?)
            .shouldNotBeNull()
            .shouldBeTrue()
    }

    @Test
    fun shouldSearchWiki() {
        page.navigate("https://www.wikipedia.org/")
        page.locator("input[name=\"search\"]").click()
        page.locator("input[name=\"search\"]").fill("playwright")
        page.locator("input[name=\"search\"]").press("Enter")
        page.locator("input[name=\"search\"]").first().fill("playwright")
        page.locator("input[name=\"search\"]").first().press("Enter")
        page.waitForURL("https://en.wikipedia.org/wiki/Playwright")
        page.url() shouldBe "https://en.wikipedia.org/wiki/Playwright"
    }

}