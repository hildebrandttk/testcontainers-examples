package tk.hildebrandt.testcontainers.geb

import io.cucumber.java.en.When
import org.openqa.selenium.chrome.ChromeOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import tk.hildebrandt.testcontainers.TestContext
import tk.hildebrandt.testcontainers.geb.pages.LoginPage

class AdminerSteps extends GebEnabledStep {

   AdminerSteps(TestContext testContext) {
      super(testContext)
   }

   @When('^user set valid credentials for current db in web browser')
   public void login() {
      to(LoginPage).login(testContext.dbmsType)
   }
}
