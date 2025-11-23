package org.jesperancinha.enterprise

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.*

class PlaywrightTest {
    lateinit var context: BrowserContext
    lateinit var page: Page

    @BeforeEach
    fun createContextAndPage() {
        context = browser.newContext()
        page = context.newPage()
    }

    @AfterEach
    fun closeContext() {
        context.close()
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

    companion object {
        // Shared between all tests in this class.
        lateinit var playwright: Playwright
        lateinit var browser: Browser

        @JvmStatic
        @BeforeAll
        fun launchBrowser() {
            playwright = Playwright.create()
//            browser = playwright!!.chromium().launch(
//                LaunchOptions().setHeadless(false)
//            )
            browser = playwright.chromium().launch()
        }

        @JvmStatic
        @AfterAll
        fun closeBrowser() {
            playwright.close()
        }
    }
}