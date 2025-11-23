package org.jesperancinha.enterprise

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import org.junit.jupiter.api.*

class PlaywrightTest {
    lateinit var context: BrowserContext
    lateinit var page: Page

    @BeforeEach
    fun createContextAndPage() {
        context = browser!!.newContext()
        page = context!!.newPage()
    }

    @AfterEach
    fun closeContext() {
        context!!.close()
    }

    @Test
    fun shouldClickButton() {
        page!!.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>")
        page!!.locator("button").click()
        Assertions.assertEquals("Clicked", page!!.evaluate("result"))
    }

    @Test
    fun shouldCheckTheBox() {
        page!!.setContent("<input id='checkbox' type='checkbox'></input>")
        page!!.locator("input").check()
        Assertions.assertTrue((page!!.evaluate("() => window['checkbox'].checked") as Boolean?)!!)
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
        Assertions.assertEquals("https://en.wikipedia.org/wiki/Playwright", page!!.url())
    }

    companion object {
        // Shared between all tests in this class.
        var playwright: Playwright? = null
        var browser: Browser? = null

        @JvmStatic
        @BeforeAll
        fun launchBrowser() {
            playwright = Playwright.create()
            browser = playwright!!.chromium().launch(
                LaunchOptions().setHeadless(false)
            );
        }

        @JvmStatic
        @AfterAll
        fun closeBrowser() {
            playwright!!.close()
        }
    }
}