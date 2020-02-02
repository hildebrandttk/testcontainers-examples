package tk.hildebrandt.testcontainers

import io.cucumber.java.Scenario
import org.openqa.selenium.WebDriver
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.Network

class TestContext {

   private static final ThreadLocal<TestContext> INSTANCE = new ThreadLocal<>();

   DatabaseType dbmsType

   String password = 'test1234'
   Network network = Network.newNetwork()
   JdbcDatabaseContainer databaseContainer
   GenericContainer adminerContainer
   BrowserWebDriverContainer webDriverContainer
   Scenario scenario

   TestContext() {
      INSTANCE.set(this)
   }

   static WebDriver getWebDriver() {
      INSTANCE.get()?.webDriverContainer?.getWebDriver()
   }
}
