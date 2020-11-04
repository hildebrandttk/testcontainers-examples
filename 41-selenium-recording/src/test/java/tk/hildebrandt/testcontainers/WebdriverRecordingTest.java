package tk.hildebrandt.testcontainers;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static junit.framework.TestCase.assertEquals;

@Testcontainers
public class WebdriverRecordingTest {

   static {
      //redirect JUL Logs to Logback
      SLF4JBridgeHandler.removeHandlersForRootLogger();
      SLF4JBridgeHandler.install();
   }

   @Container
   public BrowserWebDriverContainer CHROME =
      new BrowserWebDriverContainer()
         .withCapabilities(new ChromeOptions())
      //TODO enable recording
      ;

   @Test
   public void searchConferenceOnBing() {
      RemoteWebDriver webDriver = CHROME.getWebDriver();
      webDriver.get("https://www.bing.com");
      WebDriverWait wait = new WebDriverWait(webDriver, 10);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("sb_form_q")));
      WebElement searchField = webDriver.findElement(By.id("sb_form_q"));
      searchField.sendKeys("wjax");
      webDriver.findElement(By.cssSelector("label[for=sb_form_go]")).click();
      WebElement conferenceLink =
         webDriver.findElement(By.cssSelector("li.b_algo h2 a"));
      assertEquals("https://jax.de/", conferenceLink.getAttribute("href"));
   }
}
