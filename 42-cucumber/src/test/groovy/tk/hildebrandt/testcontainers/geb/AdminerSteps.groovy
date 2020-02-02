package tk.hildebrandt.testcontainers.geb

import io.cucumber.java.en.When
import org.openqa.selenium.chrome.ChromeOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import tk.hildebrandt.testcontainers.TestContext
import tk.hildebrandt.testcontainers.geb.pages.LoginPage

class AdminerSteps extends GebEnabledStep {

   TestContext testContext

   AdminerSteps(TestContext testContext) {
      this.testContext = testContext
   }

   @When('^user set valid credentials for current db in web browser')
   public void login() {
      testContext.webDriverContainer = new BrowserWebDriverContainer()
         .withCapabilities(new ChromeOptions())
         .withRecordingMode(
            BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
            new File("out/cucumber"))
         .withRecordingFileFactory(new CustomRecordingFileFactory())
      testContext.webDriverContainer.withNetwork(testContext.network)
      testContext.webDriverContainer.start()
      to(LoginPage).login(testContext.dbmsType)
   }
}
